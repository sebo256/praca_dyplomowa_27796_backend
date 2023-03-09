package com.praca.dyplomowa.backend.job.models

import org.bson.types.ObjectId

data class JobApplyToRequest(
        val objectId: ObjectId,
        val jobAppliedTo: Collection<String>
)
