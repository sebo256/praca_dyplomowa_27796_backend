package com.praca.dyplomowa.backend.client.models

data class ClientRequestUpdate(
        val objectId: String,
        val companyName: String?,
        val name: String,
        val surname: String,
        val street: String,
        val postalCode: String?,
        val city: String,
        val phoneNumber: String?,
        val email: String?
)
