package com.praca.dyplomowa.backend.security.auth

import com.praca.dyplomowa.backend.security.jwtUtils.BearerToken
import com.praca.dyplomowa.backend.security.jwtUtils.InvalidBearerToken
import com.praca.dyplomowa.backend.security.jwtUtils.JWTService
import kotlinx.coroutines.reactor.awaitSingleOrNull
import kotlinx.coroutines.reactor.mono
import org.springframework.security.authentication.ReactiveAuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.ReactiveUserDetailsService
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class AuthManager(
        private val jwtService: JWTService,
        private val users: ReactiveUserDetailsService,
        ): ReactiveAuthenticationManager {
    override fun authenticate(authentication: Authentication): Mono<Authentication> {
        return Mono.justOrEmpty(authentication)
                .filter{ auth -> auth is BearerToken }
                .cast(BearerToken::class.java)
                .flatMap { jwt -> mono { validate(jwt )}}
                .onErrorReturn(authentication)
                .onErrorMap { error -> InvalidBearerToken(error.message) }
    }
    private suspend fun validate(token: BearerToken): Authentication {
        val username = jwtService.decodeAccessToken(token.accessToken).subject
        val user = users.findByUsername(username).awaitSingleOrNull()
        return UsernamePasswordAuthenticationToken(user!!.username, user.password, user.authorities)
    }

}