package com.praca.dyplomowa.backend.authentication.refreshToken

import com.praca.dyplomowa.backend.authentication.refreshToken.models.RefreshTokenResponse
import com.praca.dyplomowa.backend.authentication.refreshToken.usecase.IRefreshTokenUseCase
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpHeaders
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.adapter.rxjava.RxJava3Adapter.singleToMono
import reactor.core.publisher.Mono
import java.util.*

@RestController
@RequestMapping("/auth")
class RefreshTokenController(
        private val refreshTokenUseCase: IRefreshTokenUseCase,
        @Value("\${jwt.cookieName}") private val cookieName: String) {

    @PostMapping("/refreshtoken")
    fun refreshToken(@RequestHeader(HttpHeaders.COOKIE) cookie: Optional<String>): Mono<RefreshTokenResponse> =
            singleToMono(refreshTokenUseCase.getAccessToken(cookie.get().removePrefix(cookieName + "=")))
}