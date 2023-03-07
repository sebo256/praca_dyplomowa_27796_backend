package com.praca.dyplomowa.backend.authentication.registration.models

import org.bson.types.ObjectId

data class RegistrationResponse(
        val status: Boolean,
        val message: String,
        val account: RegistrationSuccess?,
)

data class RegistrationSuccess(
        val id: ObjectId,
        var username: String
)
