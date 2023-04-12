package com.praca.dyplomowa.backend.user.service

import com.praca.dyplomowa.backend.user.models.UserGetAllResponse
import com.praca.dyplomowa.backend.user.models.UserGetAllResponseCollection
import io.reactivex.rxjava3.core.Single

interface IUserService {

    fun getUsers(): Single<UserGetAllResponseCollection>

    fun getUser(username: String): Single<UserGetAllResponse>

}