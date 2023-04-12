package com.praca.dyplomowa.backend.authentication.refreshToken.service

import com.praca.dyplomowa.backend.authentication.refreshToken.models.RefreshTokenResponse
import io.reactivex.rxjava3.core.Single

interface IRefreshTokenService {

    fun getAccessToken(token: String): Single<RefreshTokenResponse>

}