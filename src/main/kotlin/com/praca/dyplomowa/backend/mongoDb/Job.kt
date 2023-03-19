package com.praca.dyplomowa.backend.mongoDb

import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer
import io.reactivex.rxjava3.core.Single
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.DocumentReference
import org.springframework.data.mongodb.core.mapping.MongoId

@Document(collection = "job")
data class Job(
        @Id
        val id: ObjectId = ObjectId.get(),
        val companyName: String?,
        val name: String,
        val surname: String,
        val street: String,
        val postalCode: String?,
        val city: String,
        val phoneNumber: String?,
        val email: String?,
        val subject: String,
        val dateOfCreation: Long,
        val plannedDate: Long?,
        val timeSpent: Int,
        val note: String?,
        val isCompleted: Boolean,
        @DocumentReference
        val createdBy: User,
//        @DocumentReference
        val jobAppliedTo: Collection<String>? = null
)
