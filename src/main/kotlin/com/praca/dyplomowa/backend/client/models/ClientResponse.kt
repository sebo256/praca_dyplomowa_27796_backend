package com.praca.dyplomowa.backend.client.models

import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer
import org.bson.types.ObjectId

data class ClientResponse(
        @JsonSerialize(using = ToStringSerializer::class)
        val id: ObjectId? = null,
        val status: Boolean,
        val message: String
)
