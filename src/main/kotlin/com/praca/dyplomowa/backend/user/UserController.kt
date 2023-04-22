package com.praca.dyplomowa.backend.user

import com.praca.dyplomowa.backend.user.models.UserResponse
import com.praca.dyplomowa.backend.user.models.UserGetAllResponse
import com.praca.dyplomowa.backend.user.models.UserGetAllResponseCollection
import com.praca.dyplomowa.backend.user.service.IUserService
import io.reactivex.rxjava3.core.Single
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono
import java.util.NoSuchElementException

@RestController
@RequestMapping("/user")
class UserController(private val userService: IUserService) {

    @GetMapping
    fun getUsers(): Single<UserGetAllResponseCollection> =
            userService.getUsers()

    @GetMapping("/{username}")
    fun getUser(@PathVariable username: String): Single<UserGetAllResponse> =
            userService.getUser(username)

    @ExceptionHandler(NoSuchElementException::class)
    fun noSuchElementExceptionUser(): Mono<UserResponse> =
            ResponseStatusException(HttpStatus.NOT_FOUND).toMono()
}