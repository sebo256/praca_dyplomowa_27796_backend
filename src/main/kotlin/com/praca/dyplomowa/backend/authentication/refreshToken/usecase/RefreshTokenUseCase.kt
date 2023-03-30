package com.praca.dyplomowa.backend.authentication.refreshToken.usecase

import com.auth0.jwt.exceptions.TokenExpiredException
import com.praca.dyplomowa.backend.authentication.refreshToken.models.RefreshTokenResponse
import com.praca.dyplomowa.backend.logger.IApplicationLogger
import com.praca.dyplomowa.backend.mongoDb.repository.UserRepository
import com.praca.dyplomowa.backend.security.jwtUtils.IJWTService
import io.reactivex.rxjava3.core.Single
import org.springframework.stereotype.Service

@Service
class RefreshTokenUseCase(private val jwtService: IJWTService,
                          private val userRepository: UserRepository,
                          private val logger: IApplicationLogger): IRefreshTokenUseCase {

    override fun getAccessToken(token: String): Single<RefreshTokenResponse> =
            userRepository.findByUsername(decodeToken(token))
                    .map { toSuccessResponse(jwtService.accessToken(it)) }
                    .onErrorReturn {
                        RefreshTokenResponse(
                                status = false,
                                token = token,
                                message = FAILED_MESSAGE
                        )
                    }

    fun decodeToken(token: String): String {
        return jwtService.decodeRefreshToken(token).subject
    }


    private fun toSuccessResponse(token: String) =
            RefreshTokenResponse(
                    status = true,
                    token = token,
                    message = SUCCESS_MESSAGE
            )

    companion object {
        private const val FAILED_MESSAGE = "Refresh token is expired"
        private const val SUCCESS_MESSAGE = "Renewed access token"
    }
}