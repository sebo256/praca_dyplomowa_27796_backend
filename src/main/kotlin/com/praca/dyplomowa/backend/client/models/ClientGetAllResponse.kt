package com.praca.dyplomowa.backend.client.models

import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer
import org.bson.types.ObjectId

data class ClientGetAllResponse(
        @JsonSerialize(using = ToStringSerializer::class)
        val id: ObjectId = ObjectId.get(),
        val companyName: String?,
        val name: String,
        val surname: String,
        val street: String,
        val postalCode: String?,
        val city: String,
        val phoneNumber: String?,
        val email: String?
)

data class ClientGetAllResponseCollection(
        val collection: Collection<ClientGetAllResponse>
)
