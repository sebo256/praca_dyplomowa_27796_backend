package com.praca.dyplomowa.backend.security.auth

import com.praca.dyplomowa.backend.security.jwtUtils.BearerToken
import org.springframework.stereotype.Component
import org.springframework.http.HttpHeaders
import org.springframework.security.core.Authentication
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

@Component
class ServerAuthConverter: ServerAuthenticationConverter {

    override fun convert(exchange: ServerWebExchange): Mono<Authentication> {
        return Mono.justOrEmpty(exchange.request.headers)
                .filter{ !it.getFirst(HttpHeaders.AUTHORIZATION).isNullOrBlank()}
                .filter{ !it.getFirst(HttpHeaders.COOKIE).isNullOrBlank()}
                .map{ BearerToken(getAccessToken(it), getRefreshToken(it)) }
    }

    private fun getAccessToken(headers: HttpHeaders) =
            when (headers.getFirst(HttpHeaders.AUTHORIZATION)?.contains("Bearer")){
                true -> headers.getFirst(HttpHeaders.AUTHORIZATION)!!.substring(7)
                else -> ACCESSTOKEN_EMPTY
            }

    private fun getRefreshToken(headers: HttpHeaders) =
            when(headers.getFirst(HttpHeaders.COOKIE)?.contains(COOKIE_NAME)){
                true -> headers.getFirst(HttpHeaders.COOKIE)!!.substring(19)
                else -> REFRESHTOKEN_EMPTY
            }

    companion object {
        private const val ACCESSTOKEN_EMPTY = "AccessToken not found"
        private const val REFRESHTOKEN_EMPTY = "RefreshToken not found"
        private const val COOKIE_NAME = "pdyplomowa-refersh"
    }
}