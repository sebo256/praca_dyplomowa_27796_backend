package com.praca.dyplomowa.backend.authentication.login.usecase

import com.praca.dyplomowa.backend.authentication.login.models.AuthResponse
import com.praca.dyplomowa.backend.authentication.login.models.LoginRequest
import io.reactivex.rxjava3.core.Single

interface ILoginUseCase {

    fun getUser(request: LoginRequest): Single<AuthResponse>

}