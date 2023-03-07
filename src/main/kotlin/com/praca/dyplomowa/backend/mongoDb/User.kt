package com.praca.dyplomowa.backend.mongoDb

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "user")
data class User(
        @Id
        val id: ObjectId = ObjectId.get(),
        @Indexed(unique = true)
        val username: String,
        val password: String,
        val name: String,
        val surname: String,
        val roles: Collection<String>,
        val refreshToken: String? = null
)
