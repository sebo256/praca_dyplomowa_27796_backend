package com.praca.dyplomowa.backend.authentication.logout.usecase

import io.reactivex.rxjava3.core.Single

interface ILogoutUseCase {

    fun logout(token: String): Single<String>

}