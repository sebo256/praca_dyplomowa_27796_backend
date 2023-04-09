package com.praca.dyplomowa.backend.job.usecase

import com.praca.dyplomowa.backend.job.models.*
import io.reactivex.rxjava3.core.Single

interface IJobUseCase {

    fun createJob(request: JobRequest): Single<JobResponse>

    fun addJobApplyTo(request: JobApplyToRequest): Single<JobResponse>

    fun getJobs(): Single<JobGetAllResponseCollection>

    fun getJobsForList(): Single<JobGetForListResponseCollection>

    fun getDatesAndInfo(): Single<JobGetDatesAndInfoResponseCollection>

    fun getJobById(objectId: String): Single<JobGetAllResponse>

    fun getJobAppliedTo(objectId: String): Single<JobAppliedToResponse>

    fun getJobsAppliedToUserAndCheckCompleted(username: String, isCompleted: Boolean): Single<JobGetForListResponseCollection>

    fun countJobsAppliedToUserAndCheckCompleted(username: String, isCompleted: Boolean): Single<Long>

    fun getJobByLongDateBetween(startLong: Long, endLong: Long): Single<JobGetAllResponseCollection>

    fun getSumOfTimeSpentForSpecifiedMonthAndUserAndCheckCompleted(startLong: Long, endLong: Long, username: String, isCompleted: Boolean): Single<Int>

    fun getAllTimeSpentForUserPerMonth(username: String): Single<JobTimeSpentResponseCollection>

    fun updateJob(request: JobRequestUpdate): Single<JobResponse>

    fun deleteJob(objectId: String): Single<JobResponse>
}