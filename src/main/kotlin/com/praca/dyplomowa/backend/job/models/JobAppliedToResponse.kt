package com.praca.dyplomowa.backend.job.models

import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer
import org.bson.types.ObjectId

data class JobAppliedToResponse(
        @JsonSerialize(using = ToStringSerializer::class)
        val id: ObjectId? = null,
        val status: Boolean,
        val message: String,
        val jobAppliedTo: Collection<String>
)
