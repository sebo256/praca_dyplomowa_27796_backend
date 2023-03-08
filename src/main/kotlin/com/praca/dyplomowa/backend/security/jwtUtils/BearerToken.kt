package com.praca.dyplomowa.backend.security.jwtUtils

import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.core.authority.AuthorityUtils

class BearerToken(val accessToken: String, val refreshToken: String): AbstractAuthenticationToken(AuthorityUtils.NO_AUTHORITIES) {
    override fun getCredentials(): Any = accessToken
    override fun getPrincipal(): Any = accessToken
}