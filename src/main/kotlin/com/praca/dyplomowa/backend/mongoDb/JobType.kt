package com.praca.dyplomowa.backend.mongoDb

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "jobType")
data class JobType(
        @Id
        val id: ObjectId = ObjectId.get(),
        val jobType: String
)
