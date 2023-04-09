package com.praca.dyplomowa.backend.job.models

data class JobTimeSpentResponse (
    val name: String,
    val timeSpent: Int
)

data class JobTimeSpentResponseCollection(
        val collection: Collection<JobTimeSpentResponse>
)