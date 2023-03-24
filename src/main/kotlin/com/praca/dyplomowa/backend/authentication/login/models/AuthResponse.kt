package com.praca.dyplomowa.backend.authentication.login.models

import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer
import org.bson.types.ObjectId

data class AuthResponse(
        val jwt: String? = null,
        val refreshToken: String? = null,
        val message: String,
        val username: String? = null
)
