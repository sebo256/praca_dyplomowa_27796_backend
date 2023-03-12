package com.praca.dyplomowa.backend.job.models


data class JobApplyToRequest(
        val objectId: String,
        val jobAppliedTo: Collection<String>
)
