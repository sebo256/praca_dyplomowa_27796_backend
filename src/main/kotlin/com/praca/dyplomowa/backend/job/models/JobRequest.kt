package com.praca.dyplomowa.backend.job.models

data class JobRequest(
        val client: String,
        val subject: String,
        val jobType: String,
        val dateOfCreation: Long,
        val plannedDate: Long?,
        val timeSpent: Int,
        val note: String?,
        val isCompleted: Boolean,
        val createdBy: String
)
