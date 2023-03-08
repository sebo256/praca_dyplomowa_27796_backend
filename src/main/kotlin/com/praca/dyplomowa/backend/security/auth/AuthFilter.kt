package com.praca.dyplomowa.backend.security.auth

import com.praca.dyplomowa.backend.security.jwtUtils.IJWTService
import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.ReactiveSecurityContextHolder
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Mono

class AuthFilter(private val jwtService: IJWTService) : WebFilter {

    override fun filter(exchange: ServerWebExchange, chain: WebFilterChain): Mono<Void> =
            getAuthHeader(exchange)
                    .runCatching {
                        val token = jwtService.decodeAccessToken(this!!)
                        val auth = UsernamePasswordAuthenticationToken(token.subject, null, jwtService.getRoles(token))
                        return chain.filter(exchange).contextWrite(ReactiveSecurityContextHolder.withAuthentication(auth))
                    }
                    .onFailure {
                        return chain.filter(exchange).contextWrite(ReactiveSecurityContextHolder.clearContext())
                    }
                    .getOrElse {
                        return chain.filter(exchange)
                    }


    private fun getAuthHeader(exchange: ServerWebExchange) =
            exchange.request.headers.getFirst(HttpHeaders.AUTHORIZATION)
}