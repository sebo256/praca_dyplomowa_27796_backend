package com.praca.dyplomowa.backend.security.jwtUtils

import com.auth0.jwt.interfaces.DecodedJWT
import com.praca.dyplomowa.backend.mongoDb.User
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

interface IJWTService {

    fun accessToken(user: User): String
    fun decodeAccessToken(accessToken: String): DecodedJWT
    fun refreshToken(user: User): String
    fun decodeRefreshToken(refreshToken: String): DecodedJWT
    fun getRoles(decodedJWT: DecodedJWT): List<SimpleGrantedAuthority>
    fun isValid(token: String, user: UserDetails?): Boolean
    fun refreshIsExpired(token: String): Boolean
    fun accessIsExpired(token: String): Boolean
}