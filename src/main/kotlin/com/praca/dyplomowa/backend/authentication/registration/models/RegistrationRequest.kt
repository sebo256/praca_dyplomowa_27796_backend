package com.praca.dyplomowa.backend.authentication.registration.models

data class RegistrationRequest(
        val username: String,
        val password: String,
        val name: String,
        val surname: String
)
