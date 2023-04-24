package com.praca.dyplomowa.backend.authentication.refreshToken

import com.auth0.jwt.exceptions.TokenExpiredException
import com.praca.dyplomowa.backend.authentication.refreshToken.models.RefreshTokenResponse
import com.praca.dyplomowa.backend.authentication.refreshToken.service.IRefreshTokenService
import com.praca.dyplomowa.backend.authentication.registration.models.RegistrationResponse
import com.praca.dyplomowa.backend.logger.IApplicationLogger
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import reactor.adapter.rxjava.RxJava3Adapter.singleToMono
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono
import java.util.*

@RestController
@RequestMapping("/auth")
class RefreshTokenController(
        private val refreshTokenService: IRefreshTokenService,
        val logger: IApplicationLogger,
        @Value("\${jwt.cookieName}") private val cookieName: String) {

    @PostMapping("/refreshtoken")
    fun refreshToken(@RequestHeader(HttpHeaders.COOKIE) cookie: Optional<String>): Mono<RefreshTokenResponse> =
            singleToMono(refreshTokenService.getAccessToken(cookie.get().removePrefix(cookieName + "=").split(";").get(0)))


    @ExceptionHandler(IllegalStateException::class)
    fun illeaglStateException(exc: IllegalStateException): Mono<RegistrationResponse> {
        logger.error("RefreshTokenController " + exc.toString())
        return ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR).toMono()
    }

    @ExceptionHandler(TokenExpiredException::class)
    fun tokenExpiredException(): Mono<RefreshTokenResponse> =
            RefreshTokenResponse(
                    status = false,
                    message = "Refresh token is expired"
            ).toMono()
}