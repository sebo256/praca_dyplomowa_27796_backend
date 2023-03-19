package com.praca.dyplomowa.backend.user.models

import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer
import org.bson.types.ObjectId
data class UserGetAllResponse(
        @JsonSerialize(using = ToStringSerializer::class)
        val id: ObjectId = ObjectId.get(),
        val username: String,
        val name: String,
        val surname: String,
)

data class UserGetAllResponseCollection(
        val collection: Collection<UserGetAllResponse>
)
