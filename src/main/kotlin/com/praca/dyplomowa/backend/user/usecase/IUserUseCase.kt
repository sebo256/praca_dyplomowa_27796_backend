package com.praca.dyplomowa.backend.user.usecase

import com.praca.dyplomowa.backend.user.models.UserGetAllResponseCollection
import io.reactivex.rxjava3.core.Single

interface IUserUseCase {

    fun getUsers(): Single<UserGetAllResponseCollection>

}