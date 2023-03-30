package com.praca.dyplomowa.backend.authentication.refreshToken.models

data class RefreshTokenResponse(
        val status: Boolean,
        val token: String? = null,
        val message: String
)
