package com.praca.dyplomowa.backend.authentication.login.usecase

import com.praca.dyplomowa.backend.authentication.login.models.AuthResponse
import com.praca.dyplomowa.backend.authentication.login.models.LoginRequest
import com.praca.dyplomowa.backend.logger.IApplicationLogger
import com.praca.dyplomowa.backend.mongoDb.User
import com.praca.dyplomowa.backend.mongoDb.repository.UserRepository
import com.praca.dyplomowa.backend.security.SecurityConfig
import com.praca.dyplomowa.backend.security.jwtUtils.IJWTService
import io.reactivex.rxjava3.core.Single
import org.springframework.stereotype.Service

@Service
class LoginUseCase(
        private val logger: IApplicationLogger,
        private val userRepository: UserRepository,
        private val jwtService: IJWTService
): ILoginUseCase {

    override fun getUser(request: LoginRequest): Single<AuthResponse> =
        userRepository.findByUsername(request.username)
                .flatMap { user ->
                    when(user.checkUserPassword(request)){
                        true -> updateUser(user.copy(refreshToken = checkRefreshToken(user)))
                        false -> Single.just(errorResponse())
                    }
                }
                .onErrorReturn {
                    logger.warn("Login failed, user with given username not exist. $it")
                    errorResponse()
                }


    private fun User.checkUserPassword(loginRequest: LoginRequest) =
            SecurityConfig().passwordEncoder().matches(loginRequest.password, this.password)


    private fun errorResponse() =
            AuthResponse(
                    message = "Login failed. Given username or password is incorrect."
            )

    private fun updateUser(user: User) =
            userRepository.save(user).map{it.successResponse()}

    private fun checkRefreshToken(user: User): String{
        if(user.refreshToken.isNullOrBlank()) return jwtService.refreshToken(user)

        return when(jwtService.refreshIsExpired(user.refreshToken)){
            true -> jwtService.refreshToken(user)
            false -> user.refreshToken!!
        }
    }

    private fun User.successResponse() =
            AuthResponse(
                    jwt = jwtService.accessToken(this),
                    refreshToken = this.refreshToken,
                    message = "Successfully logged-in",
                    username = this.username
            )
}