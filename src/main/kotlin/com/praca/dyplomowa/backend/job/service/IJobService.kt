package com.praca.dyplomowa.backend.job.service

import com.praca.dyplomowa.backend.job.models.*
import io.reactivex.rxjava3.core.Single

interface IJobService {

    fun createJob(request: JobRequest): Single<JobResponse>

    fun addJobApplyTo(request: JobApplyToRequest): Single<JobResponse>

    fun getJobs(): Single<JobGetAllResponseCollection>

    fun getJobsForList(): Single<JobGetForListResponseCollection>

    fun getDatesAndInfo(): Single<JobGetDatesAndInfoResponseCollection>

    fun getJobById(objectId: String): Single<JobGetAllResponse>

    fun getJobAppliedTo(objectId: String): Single<JobAppliedToResponse>

    fun getJobsAppliedToUserAndCheckCompleted(username: String, isCompleted: Boolean): Single<JobGetForListResponseCollection>

    fun countJobsAppliedToUserAndCheckCompleted(username: String, isCompleted: Boolean): Single<Long>

    fun getJobByLongDateBetween(startLong: Long, endLong: Long): Single<JobGetForListResponseCollection>

    fun getSumOfTimeSpentForSpecifiedMonthAndUser(startLong: Long, endLong: Long, username: String): Single<Int>

    fun getJobsForSpecifiedMonthAndUser(startLong: Long, endLong: Long, username: String): Single<JobGetForListHoursResponseCollection>

    fun getAllTimeSpentForUserPerMonth(username: String): Single<JobTimeSpentResponseCollection>

    fun updateJob(request: JobRequestUpdate): Single<JobResponse>

    fun addTimeSpent(request: JobAddTimeSpentRequest): Single<JobResponse>

    fun deleteJob(objectId: String): Single<JobResponse>
}