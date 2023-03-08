package com.praca.dyplomowa.backend.security.jwtUtils

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.interfaces.DecodedJWT
import com.praca.dyplomowa.backend.mongoDb.User
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.util.Date

@Service
class JWTService(@Value("\${jwt.secretAccess}") val secret: String,
                 @Value("\${jwt.secretRefresh}") val refresh: String,
                 @Value("\${jwt.expirationInMs}") val accessTokenExpiration: Int,
                 @Value("\${jwt.refreshExpirationInMs}") val refreshTokenExpiration: Int): IJWTService {

    private fun generate(username: String, expirationInMs: Int, roles: Collection<String>, signature: String): String{
        return JWT.create()
                .withSubject(username)
                .withExpiresAt(Date(System.currentTimeMillis() + expirationInMs))
                .withArrayClaim("role", roles.toTypedArray())
                .sign(Algorithm.HMAC512(signature.toByteArray()))
    }
    private fun decode(signature: String, token: String): DecodedJWT {
        return JWT.require(Algorithm.HMAC512(signature.toByteArray()))
                .build()
                .verify(token.replace("Bearer ", ""))
    }

    override fun accessToken(user: User): String {
        return generate(user.username, accessTokenExpiration, user.roles, secret)
    }

    override fun decodeAccessToken(accessToken: String): DecodedJWT {
        return decode(secret, accessToken)
    }

    override fun refreshToken(user: User): String {
        return generate(user.username, refreshTokenExpiration, user.roles, refresh)
    }

    override fun decodeRefreshToken(refreshToken: String): DecodedJWT {
        return decode(refresh, refreshToken)
    }

    override fun getRoles(decodedJWT: DecodedJWT) = decodedJWT.getClaim("role").asList(String::class.java)
            .map{ SimpleGrantedAuthority(it) }


    override fun isValid(token: String, user: UserDetails?) =
        decodeAccessToken(token).subject == user?.username && accessIsExpired(token)


    override fun refreshIsExpired(token: String) =
        decodeRefreshToken(token).expiresAt.after(Date())


    override fun accessIsExpired(token: String) =
        decodeAccessToken(token).expiresAt.after(Date())
}