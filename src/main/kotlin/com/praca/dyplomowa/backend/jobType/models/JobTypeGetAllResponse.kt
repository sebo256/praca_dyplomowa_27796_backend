package com.praca.dyplomowa.backend.jobType.models

import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer
import org.bson.types.ObjectId

data class JobTypeGetAllResponse(
        @JsonSerialize(using = ToStringSerializer::class)
        val id: ObjectId = ObjectId.get(),
        val jobType: String
)

data class JobTypeGetAllResponseCollection(
        val collection: Collection<JobTypeGetAllResponse>
)
