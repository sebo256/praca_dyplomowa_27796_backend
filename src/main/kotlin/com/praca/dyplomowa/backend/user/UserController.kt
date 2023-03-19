package com.praca.dyplomowa.backend.user

import com.praca.dyplomowa.backend.user.models.UserGetAllResponseCollection
import com.praca.dyplomowa.backend.user.usecase.IUserUseCase
import io.reactivex.rxjava3.core.Single
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/user")
class UserController(private val userUseCase: IUserUseCase) {

    @GetMapping
    fun getUsers(): Single<UserGetAllResponseCollection> =
            userUseCase.getUsers()

}