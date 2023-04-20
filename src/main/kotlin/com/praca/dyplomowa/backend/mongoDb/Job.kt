package com.praca.dyplomowa.backend.mongoDb

import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.DocumentReference

@Document(collection = "job")
data class Job(
        @Id
        @JsonSerialize(using = ToStringSerializer::class)
        val id: ObjectId = ObjectId.get(),
        @DocumentReference
        val client: Client,
        val subject: String,
        @DocumentReference
        val jobType: JobType,
        val dateOfCreation: Long,
        val plannedDate: Long?,
        val timeSpent: Int,
        val note: String?,
        val isCompleted: Boolean,
        @DocumentReference
        val createdBy: User,
        val jobAppliedTo: Collection<String>? = null
)
