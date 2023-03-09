package com.praca.dyplomowa.backend.job.usecase

import com.praca.dyplomowa.backend.job.models.JobRequest
import com.praca.dyplomowa.backend.job.models.JobResponse
import io.reactivex.rxjava3.core.Single

interface IJobUseCase {

    fun createJob(request: JobRequest): Single<JobResponse>

}