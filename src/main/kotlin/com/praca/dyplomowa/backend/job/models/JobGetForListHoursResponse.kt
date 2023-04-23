package com.praca.dyplomowa.backend.job.models

import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer
import org.bson.types.ObjectId

data class JobGetForListHoursResponse(
        @JsonSerialize(using = ToStringSerializer::class)
        val id: ObjectId = ObjectId.get(),
        val subject: String,
        val jobType: String,
        val companyName: String?,
        val name: String,
        val surname: String,
        val street: String,
        val city: String,
        val isCompleted: Boolean,
        val timeSpent: Int,
)

data class JobGetForListHoursResponseCollection(
        val collection: Collection<JobGetForListHoursResponse>
)
