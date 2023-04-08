package com.praca.dyplomowa.backend.job.models

import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer
import org.bson.types.ObjectId

data class JobGetForListResponse(
        @JsonSerialize(using = ToStringSerializer::class)
        val id: ObjectId = ObjectId.get(),
        val subject: String,
        val companyName: String?,
        val name: String,
        val surname: String,
        val street: String,
        val city: String,
        val isCompleted: Boolean
)

data class JobGetForListResponseCollection(
        val collection: Collection<JobGetForListResponse>
)
