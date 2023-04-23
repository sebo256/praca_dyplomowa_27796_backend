package com.praca.dyplomowa.backend.job.service

import com.praca.dyplomowa.backend.job.models.*
import com.praca.dyplomowa.backend.mongoDb.Client
import com.praca.dyplomowa.backend.mongoDb.Job
import com.praca.dyplomowa.backend.mongoDb.JobType
import com.praca.dyplomowa.backend.mongoDb.User
import com.praca.dyplomowa.backend.mongoDb.repository.ClientRepository
import com.praca.dyplomowa.backend.mongoDb.repository.JobRepository
import com.praca.dyplomowa.backend.mongoDb.repository.JobTypeRepository
import com.praca.dyplomowa.backend.mongoDb.repository.UserRepository
import io.reactivex.rxjava3.core.Single
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import java.time.Instant
import java.time.ZoneId

@Service
class JobService(
        private val userRepository: UserRepository,
        private val jobRepository: JobRepository,
        private val jobTypeRepository: JobTypeRepository,
        private val clientRepository: ClientRepository
):IJobService {

    override fun createJob(request: JobRequest): Single<JobResponse> =
            getUserAndJobTypeAndClientForNewJob(request).flatMap {
                saveJob(
                    request = request,
                    user = it.first,
                    jobType = it.second,
                    client = it.third
                )
            }

    private fun getUserAndJobTypeAndClientForNewJob(request: JobRequest) =
            Single.zip(
                    userRepository.findByUsername(request.createdBy),
                    jobTypeRepository.findById(request.jobType).toSingle(),
                    clientRepository.findById(request.client).toSingle()
            ) { user, jobType, client ->
                Triple(user, jobType, client)
            }

    private fun saveJob(request: JobRequest, user: User, jobType: JobType, client: Client) =
            saveJob(request.toJob(user, jobType, client))

    override fun addJobApplyTo(request: JobApplyToRequest): Single<JobResponse> =
            jobRepository.findById(request.objectId).toSingle().flatMap { saveJob(it.copy(jobAppliedTo = request.jobAppliedTo)) }

    fun saveJob(job: Job) =
            jobRepository.save(job).map { it.toNewJobResponse() }

    override fun getJobs(): Single<JobGetAllResponseCollection> =
            jobRepository.findAll(Sort.by(Sort.Direction.DESC, "dateOfCreation")).toList().map {
                JobGetAllResponseCollection(
                        it.map {
                            it.toJobResponse()
                        }
                )
            }

    override fun getJobsForList(): Single<JobGetForListResponseCollection> =
            jobRepository.findAll(Sort.by(Sort.Direction.DESC, "dateOfCreation")).toList().map {
                JobGetForListResponseCollection(
                        it.map{
                            it.toJobForListResponse()
                        }
                )
            }


    override fun getDatesAndInfo(): Single<JobGetDatesAndInfoResponseCollection> =
            jobRepository.findAll().filter { it.plannedDate != null }.toList().map {
                        JobGetDatesAndInfoResponseCollection(
                            it.map {
                                it.toGetDatesAndInfoResponse()
                            }
                )
            }


    override fun getJobById(objectId: String): Single<JobGetAllResponse> =
            jobRepository.findById(objectId).toSingle().map { it.toJobResponse() }

    override fun getJobAppliedTo(objectId: String): Single<JobAppliedToResponse> =
            jobRepository.findById(objectId).toSingle().map { it.toGetJobAppliedToResponse() }

    override fun getJobsAppliedToUserAndCheckCompleted(username: String, isCompleted: Boolean): Single<JobGetForListResponseCollection> =
            jobRepository.findAllByJobAppliedToContainingAndIsCompleted(jobAppliedTo = username, isCompleted = isCompleted).toList().map {
                JobGetForListResponseCollection(
                        it.map{
                            it.toJobForListResponse()
                        }
                )
            }

    override fun countJobsAppliedToUserAndCheckCompleted(username: String, isCompleted: Boolean): Single<Long> =
            jobRepository.countAllByJobAppliedToContainingAndIsCompleted(username, isCompleted)


    override fun getJobByLongDateBetween(startLong: Long, endLong: Long): Single<JobGetForListResponseCollection> =
            jobRepository.findAllByPlannedDateBetween(startLong = startLong, endLong = endLong).toList().map {
                JobGetForListResponseCollection(
                        it.map {
                            it.toJobForListResponse()
                        }
                )
            }

    override fun getSumOfTimeSpentForSpecifiedMonthAndUser(startLong: Long, endLong: Long, username: String): Single<Int> =
            jobRepository.findAllByPlannedDateBetweenAndJobAppliedToContaining(
                    startLong = startLong,
                    endLong = endLong,
                    jobAppliedTo = username,
            ).toList().map {
                        it.sumOf { it.timeSpent!!.getValue(username) }
            }

    override fun getJobsForSpecifiedMonthAndUser(startLong: Long, endLong: Long, username: String): Single<JobGetForListHoursResponseCollection> =
            jobRepository.findAllByPlannedDateBetweenAndJobAppliedToContaining(
                    startLong = startLong,
                    endLong = endLong,
                    jobAppliedTo = username
            ).toList().map {
                JobGetForListHoursResponseCollection(
                        it.map {
                            it.toJobForListHoursResponse(username)
                        }
                )
            }


    override fun getAllTimeSpentForUserPerMonth(username: String): Single<JobTimeSpentResponseCollection> =
           jobRepository.findAllByJobAppliedToContainingOrderByPlannedDateAsc(username).toList().map {
               JobTimeSpentResponseCollection(
                        it.map {
                            JobTimeSpentResponse(
                                    name = longToMonthYearString(it.plannedDate!!),
                                    timeSpent = it.timeSpent!!.getValue(username)
                            )
                        }.groupingBy { it.name }
                                .reduce { key, accumulator, element -> JobTimeSpentResponse(name = element.name, timeSpent = accumulator.timeSpent + element.timeSpent) }
                                .values
                )
           }

    override fun deleteJob(objectId: String): Single<JobResponse> =
            jobRepository.deleteById(objectId).toSingleDefault(deleteResponse())

    override fun updateJob(request: JobRequestUpdate): Single<JobResponse> =
            getJobAndJobTypeAndClientForJobUpdate(request).flatMap {
                saveJob(
                        it.first.copy(
                                client = it.third,
                                subject =  request.subject,
                                jobType = it.second,
                                plannedDate = request.plannedDate,
                                note = request.note,
                                isCompleted = request.isCompleted,
                        )
                )
            }

    override fun addTimeSpent(request: JobAddTimeSpentRequest): Single<JobResponse> =
            jobRepository.findById(request.objectId).toSingle().flatMap {
                saveJob(
                        it.copy(
                                timeSpent = request.timeSpentMap
                        )
                )
            }

    private fun getJobAndJobTypeAndClientForJobUpdate(request: JobRequestUpdate) =
            Single.zip(
                    jobRepository.findById(request.objectId).toSingle(),
                    jobTypeRepository.findById(request.jobType).toSingle(),
                    clientRepository.findById(request.client).toSingle()
            ) { job, jobType, client ->
                Triple(job, jobType, client)
            }

    private fun longToMonthYearString(long: Long) =
            Instant.ofEpochMilli(long).atZone(ZoneId.systemDefault()).toLocalDate().month.toString() + " " +
                Instant.ofEpochMilli(long).atZone(ZoneId.systemDefault()).toLocalDate().year.toString()


    private fun errorResponse() =
            JobResponse(
                    status = false,
                    message = "Something went wrong"
            )


    private fun deleteResponse() =
            JobResponse(
                    status = true,
                    message = "Successfully deleted job"
            )

    private fun Job.toNewJobResponse() =
            JobResponse(
                    id = this.id,
                    status = true,
                    message = "Succesfully created new job"
            )

    private fun Job.toGetJobAppliedToResponse() =
            JobAppliedToResponse(
                    id = this.id,
                    status = true,
                    message = "Sucessfuly get users applied to this job",
                    jobAppliedTo = this.jobAppliedTo?: listOf(),
                    timeSpent = this.timeSpent
            )

    private fun Job.toGetDatesAndInfoResponse() =
            JobGetDatesAndInfoResponse(
                    id = this.id,
                    subject = this.subject,
                    plannedDate = this.plannedDate,
                    isCompleted = this.isCompleted
            )

    private fun JobRequest.toJob(user: User, jobType: JobType, client: Client) =
            Job(
                    client = client,
                    subject = this.subject,
                    jobType = jobType,
                    dateOfCreation = System.currentTimeMillis(),
                    plannedDate = this.plannedDate,
                    note = this.note,
                    isCompleted = this.isCompleted,
                    createdBy = user
            )

    private fun Job.toJobForListResponse()=
            JobGetForListResponse(
                    id = this.id,
                    subject = this.subject,
                    jobType = this.jobType.jobType,
                    companyName = this.client.companyName,
                    name = this.client.name,
                    surname = this.client.surname,
                    street = this.client.street,
                    city = this.client.city,
                    isCompleted = this.isCompleted
            )

    private fun Job.toJobForListHoursResponse(username: String) =
            JobGetForListHoursResponse(
                    id = this.id,
                    subject = this.subject,
                    jobType = this.jobType.jobType,
                    companyName = this.client.companyName,
                    name = this.client.name,
                    surname = this.client.surname,
                    street = this.client.street,
                    city = this.client.city,
                    isCompleted = this.isCompleted,
                    timeSpent = this.timeSpent!!.getValue(username)
            )

    private fun Job.toJobResponse()=
            JobGetAllResponse(
                    id = this.id,
                    client = this.client,
                    subject = this.subject,
                    jobType = this.jobType,
                    dateOfCreation = this.dateOfCreation,
                    plannedDate = this.plannedDate,
                    note = this.note,
                    isCompleted = this.isCompleted,
                    createdBy = JobUserResponse(
                            username = this.createdBy.username,
                            name = this.createdBy.name,
                            surname = this.createdBy.surname
                    ),
                    jobAppliedTo = this.jobAppliedTo?: listOf()
            )
}