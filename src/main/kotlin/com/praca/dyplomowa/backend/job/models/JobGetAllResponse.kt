package com.praca.dyplomowa.backend.job.models

import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer
import com.praca.dyplomowa.backend.mongoDb.Client
import com.praca.dyplomowa.backend.mongoDb.JobType
import org.bson.types.ObjectId

data class JobGetAllResponse(
        @JsonSerialize(using = ToStringSerializer::class)
        val id: ObjectId = ObjectId.get(),
        val client: Client,
        val subject: String,
        val jobType: JobType,
        val dateOfCreation: Long,
        val plannedDate: Long?,
        val timeSpent: Int,
        val note: String?,
        val isCompleted: Boolean,
        val createdBy: UserResponse,
        val jobAppliedTo: Collection<String>
)

data class UserResponse(
        val username: String,
        val name: String,
        val surname: String
)

data class JobGetAllResponseCollection(
        val collection: Collection<JobGetAllResponse>
)
