package com.praca.dyplomowa.backend.mongoDb.repository

import com.praca.dyplomowa.backend.mongoDb.User
import io.reactivex.rxjava3.core.Single
import org.springframework.data.repository.reactive.RxJava3CrudRepository

interface UserRepository: RxJava3CrudRepository<User, String> {

    fun findByUsername(username: String): Single<User>

}