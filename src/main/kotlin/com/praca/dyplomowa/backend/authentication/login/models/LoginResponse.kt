package com.praca.dyplomowa.backend.authentication.login.models

data class LoginResponse(
        val jwt: String? = null,
        val message: String
)
