package com.praca.dyplomowa.backend.authentication.login.models

import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer
import org.bson.types.ObjectId

data class LoginResponse(
        val jwt: String? = null,
        val message: String,
        val username: String? = null,
        val roles: Collection<String>
)
