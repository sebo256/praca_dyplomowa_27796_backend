package com.praca.dyplomowa.backend.user

import com.praca.dyplomowa.backend.user.models.UserGetAllResponse
import com.praca.dyplomowa.backend.user.models.UserGetAllResponseCollection
import com.praca.dyplomowa.backend.user.service.IUserService
import io.reactivex.rxjava3.core.Single
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/user")
class UserController(private val userService: IUserService) {

    @GetMapping
    fun getUsers(): Single<UserGetAllResponseCollection> =
            userService.getUsers()

    @GetMapping("/{username}")
    fun getUser(@PathVariable username: String): Single<UserGetAllResponse> =
            userService.getUser(username)

}