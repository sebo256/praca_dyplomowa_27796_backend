package com.praca.dyplomowa.backend.authentication.refreshToken.models

data class RefreshTokenResponse(
        val status: String,
        val token: String,
        val message: String
)
