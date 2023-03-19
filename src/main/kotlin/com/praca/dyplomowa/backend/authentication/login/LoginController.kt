package com.praca.dyplomowa.backend.authentication.login

import com.praca.dyplomowa.backend.authentication.login.models.AuthResponse
import com.praca.dyplomowa.backend.authentication.login.models.LoginRequest
import com.praca.dyplomowa.backend.authentication.login.models.LoginResponse
import com.praca.dyplomowa.backend.authentication.login.usecase.LoginUseCase
import io.reactivex.rxjava3.core.Single
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.ResponseCookie
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.adapter.rxjava.RxJava3Adapter.singleToMono
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/auth")
class LoginController(val loginUseCase: LoginUseCase) {

    @Value("\${jwt.refreshExpirationInMs}")
    private val expirationCookieTime: Long = 604800000

    @Value("\${jwt.cookieName}")
    private val cookiename: String = "pdyplomowa-refersh"
    @PostMapping("/login")
    fun login(@RequestBody loginRequest: LoginRequest): Mono<ResponseEntity<LoginResponse>> =
            singleToMono(
            loginUseCase.getUser(loginRequest)
                    .map {
                        when(it.jwt.isNullOrBlank()) {
                            true -> {
                                ResponseEntity
                                        .status(401)
                                        .body(it.toLoginResponse())
                            }
                            false -> {
                                val authCookie = ResponseCookie.fromClientResponse(cookiename, it.refreshToken!!)
                                        .maxAge(expirationCookieTime)
                                        .httpOnly(true)
                                        .path("/")
//TODO production?                                        .secure(true)
                                        .build()
                                ResponseEntity
                                        .ok()
                                        .header("Set-Cookie", authCookie.toString())
                                        .body(it.toLoginResponse())
                            }
                        }
                    }
            )

    private fun AuthResponse.toLoginResponse() =
            LoginResponse(
                    jwt = this.jwt,
                    message = this.message
            )

}