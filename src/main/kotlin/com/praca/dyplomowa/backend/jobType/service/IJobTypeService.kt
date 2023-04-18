package com.praca.dyplomowa.backend.jobType.service

import com.praca.dyplomowa.backend.jobType.models.JobTypeGetAllResponseCollection
import com.praca.dyplomowa.backend.jobType.models.JobTypeRequest
import com.praca.dyplomowa.backend.jobType.models.JobTypeResponse
import io.reactivex.rxjava3.core.Single

interface IJobTypeService {

    fun addJobType(jobTypeRequest: JobTypeRequest): Single<JobTypeResponse>

    fun getJobTypes(): Single<JobTypeGetAllResponseCollection>

}