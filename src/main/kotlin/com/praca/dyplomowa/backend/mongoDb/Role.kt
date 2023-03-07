package com.praca.dyplomowa.backend.mongoDb

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "role")
data class Role(
        @Id
        val id: ObjectId = ObjectId.get(),
        var name: ERoles
)
