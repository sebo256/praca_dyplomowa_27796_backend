package com.praca.dyplomowa.backend.authentication.logout

import com.praca.dyplomowa.backend.authentication.logout.usecase.ILogoutUseCase
import io.reactivex.rxjava3.core.Single
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpHeaders
import org.springframework.http.ResponseCookie
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("/auth")
class LogoutController(
        val logoutUseCase: ILogoutUseCase,
        @Value("\${jwt.cookieName}") val cookieName: String = "pdyplomowa-refersh"
) {

    @PostMapping("/logout")
    fun logoutUser(@RequestHeader(HttpHeaders.COOKIE) cookie: Optional<String>): Single<ResponseEntity<String>> =
            logoutUseCase.logout(cookie.get().removePrefix(cookieName + "=")).map {
                val authCookie = ResponseCookie.fromClientResponse(cookieName, "")
                        .maxAge(0)
                        .httpOnly(true)
                        .path("/")
                        .build()
                ResponseEntity
                        .ok()
                        .header("Set-Cookie", authCookie.toString())
                        .body(it)
            }

}