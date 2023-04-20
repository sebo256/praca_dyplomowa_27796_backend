package com.praca.dyplomowa.backend.mongoDb

import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "client")
data class Client(
        @Id
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
