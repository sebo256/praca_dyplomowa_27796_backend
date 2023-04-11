package com.praca.dyplomowa.backend.authentication.login.models

data class AuthResponse(
        val jwt: String? = null,
        val refreshToken: String? = null,
        val message: String,
        val username: String? = null,
        val roles: Collection<String>? = null
)
