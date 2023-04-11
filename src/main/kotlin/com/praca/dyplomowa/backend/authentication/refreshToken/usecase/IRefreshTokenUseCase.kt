package com.praca.dyplomowa.backend.authentication.refreshToken.usecase

import com.praca.dyplomowa.backend.authentication.refreshToken.models.RefreshTokenResponse
import io.reactivex.rxjava3.core.Single

interface IRefreshTokenUseCase {

    fun getAccessToken(token: String): Single<RefreshTokenResponse>

}