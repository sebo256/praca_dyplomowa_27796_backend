package com.praca.dyplomowa.backend.authentication.logout.usecase

import com.praca.dyplomowa.backend.mongoDb.User
import com.praca.dyplomowa.backend.mongoDb.repository.UserRepository
import com.praca.dyplomowa.backend.security.jwtUtils.IJWTService
import io.reactivex.rxjava3.core.Single
import org.springframework.stereotype.Service

@Service
class LogoutUseCase(private val userRepository: UserRepository,
                    private val jwtService: IJWTService) : ILogoutUseCase {

    override fun logout(token: String): Single<String> =
            userRepository.findByUsername(jwtService.decodeRefreshToken(token).subject)
                    .flatMap {
                        deleteToken(it.copy(refreshToken = null))
                    }

    private fun deleteToken(user: User) =
            userRepository.save(user).map { "Sucessfully logged out" }

}