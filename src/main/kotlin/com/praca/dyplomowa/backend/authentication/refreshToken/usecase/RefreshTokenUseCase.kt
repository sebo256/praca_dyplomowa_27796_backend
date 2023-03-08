package com.praca.dyplomowa.backend.authentication.refreshToken.usecase

import com.praca.dyplomowa.backend.authentication.refreshToken.models.RefreshTokenResponse
import com.praca.dyplomowa.backend.mongoDb.repository.UserRepository
import com.praca.dyplomowa.backend.security.jwtUtils.IJWTService
import io.reactivex.rxjava3.core.Single
import org.springframework.stereotype.Service

@Service
class RefreshTokenUseCase(private val jwtService: IJWTService,
                          private val userRepository: UserRepository): IRefreshTokenUseCase {

    override fun getAccessToken(token: String): Single<RefreshTokenResponse> =
            userRepository.findByUsername(jwtService.decodeRefreshToken(token).subject)
                    .map { toSuccessResponse(jwtService.accessToken(it)) }
                    .onErrorReturn {
                        RefreshTokenResponse(
                                status = FAILED,
                                token = token,
                                message = FAILED_MESSAGE
                        )
                    }

    private fun toSuccessResponse(token: String) =
            RefreshTokenResponse(
                    status = SUCCESS,
                    token = token,
                    message = SUCCESS_MESSAGE
            )

    companion object {
        private const val SUCCESS = "SUCCESS"
        private const val FAILED = "FAILED"
        private const val FAILED_MESSAGE = "Refresh token is expired"
        private const val SUCCESS_MESSAGE = "Renewed access token"
    }
}