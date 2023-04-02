package com.praca.dyplomowa.backend.job.usecase

import com.praca.dyplomowa.backend.job.models.*
import io.reactivex.rxjava3.core.Single

interface IJobUseCase {

    fun createJob(request: JobRequest): Single<JobResponse>

    fun addJobApplyTo(request: JobApplyToRequest): Single<JobResponse>

    fun getJobs(): Single<JobGetAllResponseCollection>

    fun getDatesAndInfo(): Single<JobGetDatesAndInfoResponseCollection>

    fun getJobById(objectId: String): Single<JobGetAllResponse>

    fun getJobAppliedTo(objectId: String): Single<JobAppliedToResponse>

    fun getJobByLongDateBetween(startLong: Long, endLong: Long): Single<JobGetAllResponseCollection>

    fun updateJob(request: JobRequestUpdate): Single<JobResponse>

    fun deleteJob(objectId: String): Single<JobResponse>
}