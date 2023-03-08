package com.praca.dyplomowa.backend.security.auth

import com.praca.dyplomowa.backend.security.jwtUtils.JWTService
import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.ReactiveSecurityContextHolder
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebFilter
import org.springframework.web.server.WebFilterChain
import reactor.core.publisher.Mono

class AuthFilter(private val jwtService: JWTService) : WebFilter {

    override fun filter(exchange: ServerWebExchange, chain: WebFilterChain): Mono<Void> {

        val authHeader = exchange.request.headers.getFirst(HttpHeaders.AUTHORIZATION) ?: return chain.filter(exchange)

        if(!authHeader.startsWith("Bearer ")) {
            return chain.filter(exchange)
        }
        try{
            val token = jwtService.decodeAccessToken(authHeader)
            val auth = UsernamePasswordAuthenticationToken(token.subject, null, jwtService.getRoles(token))
            return chain.filter(exchange).contextWrite(ReactiveSecurityContextHolder.withAuthentication(auth))
        }catch (_: Exception) {}

        return chain.filter(exchange).contextWrite(ReactiveSecurityContextHolder.clearContext())
    }

}