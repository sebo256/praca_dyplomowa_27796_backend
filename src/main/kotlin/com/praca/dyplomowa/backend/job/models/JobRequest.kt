package com.praca.dyplomowa.backend.job.models

data class JobRequest(
        val client: String,
        val subject: String,
        val jobType: String,
        val plannedDate: Long?,
        val note: String?,
        val isCompleted: Boolean,
        val createdBy: String
)
