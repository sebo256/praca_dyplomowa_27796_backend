package com.praca.dyplomowa.backend.job.models

data class JobAddTimeSpentRequest(
        val objectId: String,
        val timeSpentMap: MutableMap<String, Int>
)
