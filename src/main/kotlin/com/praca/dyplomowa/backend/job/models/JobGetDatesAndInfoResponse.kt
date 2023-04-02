package com.praca.dyplomowa.backend.job.models

import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer
import org.bson.types.ObjectId

data class JobGetDatesAndInfoResponse(
        @JsonSerialize(using = ToStringSerializer::class)
        val id: ObjectId = ObjectId.get(),
        val subject: String,
        val plannedDate: Long?,
        val isCompleted: Boolean
)

data class JobGetDatesAndInfoResponseCollection(
        val collection: Collection<JobGetDatesAndInfoResponse>
)