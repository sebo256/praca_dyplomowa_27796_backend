package com.praca.dyplomowa.backend.job.models

data class JobTimeSpentResponse (
    val name: String,
    val timeSpent: Int
)

data class JobTimeSpentResponseMap (
        val collection: Map<String, JobTimeSpentResponse>
)