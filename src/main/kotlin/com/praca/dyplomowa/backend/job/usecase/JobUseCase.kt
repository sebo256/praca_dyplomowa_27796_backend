package com.praca.dyplomowa.backend.job.usecase

import com.praca.dyplomowa.backend.job.models.*
import com.praca.dyplomowa.backend.mongoDb.Job
import com.praca.dyplomowa.backend.mongoDb.User
import com.praca.dyplomowa.backend.mongoDb.repository.JobRepository
import com.praca.dyplomowa.backend.mongoDb.repository.UserRepository
import io.reactivex.rxjava3.core.Single
import org.springframework.data.domain.Sort
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import java.time.Instant
import java.time.ZoneId

@Service
class JobUseCase(
        private val userRepository: UserRepository,
        private val jobRepository: JobRepository
):IJobUseCase {

    override fun createJob(request: JobRequest): Single<JobResponse> =
            userRepository.findByUsername(request.createdBy).flatMap { saveJob(request, it) }
                    .onErrorReturn { errorResponse() }

    private fun saveJob(request: JobRequest, user: User) =
            jobRepository.save(request.toJob(user)).map { it.toNewJobResponse() }

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


    override fun getJobByLongDateBetween(startLong: Long, endLong: Long): Single<JobGetAllResponseCollection> =
            jobRepository.findAllByPlannedDateBetween(startLong = startLong, endLong = endLong).toList().map {
                JobGetAllResponseCollection(
                        it.map {
                            it.toJobResponse()
                        }
                )
            }

    override fun getSumOfTimeSpentForSpecifiedMonthAndUserAndCheckCompleted(startLong: Long, endLong: Long, username: String, isCompleted: Boolean): Single<Int> =
            jobRepository.findAllByPlannedDateBetweenAndJobAppliedToContainingAndIsCompleted(
                    startLong = startLong,
                    endLong = endLong,
                    jobAppliedTo = username,
                    isCompleted = isCompleted
            ).toList().map {
                        it.sumOf { it.timeSpent }
            }

    override fun getAllTimeSpentForUserPerMonth(username: String): Single<JobTimeSpentResponseCollection> =
           jobRepository.findAllByJobAppliedToAndIsCompletedOrderByPlannedDateAsc(username, true).toList().map {
               JobTimeSpentResponseCollection(
                        it.map {
                            JobTimeSpentResponse(
                                    name = longToMonthYearString(it.plannedDate!!),
                                    timeSpent = it.timeSpent
                            )
                        }.groupingBy { it.name }
                                .reduce { key, accumulator, element -> JobTimeSpentResponse(name = element.name, timeSpent = accumulator.timeSpent + element.timeSpent) }
                                .values
                )
           }

    override fun deleteJob(objectId: String): Single<JobResponse> =
            jobRepository.existsById(objectId).flatMap { status ->
                when(status){
                    true -> jobRepository.deleteById(objectId).toSingleDefault(deleteResponse())
                    false -> Single.error(ResponseStatusException(HttpStatus.BAD_REQUEST))
                }
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
                    jobAppliedTo = this.jobAppliedTo?: listOf()
            )

    private fun Job.toGetDatesAndInfoResponse() =
            JobGetDatesAndInfoResponse(
                    id = this.id,
                    subject = this.subject,
                    plannedDate = this.plannedDate,
                    isCompleted = this.isCompleted
            )

    private fun JobRequest.toJob(user: User) =
            Job(
                    companyName = this.companyName,
                    name = this.name,
                    surname = this.surname,
                    street = this.street,
                    postalCode = this.postalCode,
                    city = this.city,
                    phoneNumber = this.phoneNumber,
                    email = this.email,
                    subject = this.subject,
                    jobType = this.jobType,
                    dateOfCreation = this.dateOfCreation,
                    plannedDate = this.plannedDate,
                    timeSpent = this.timeSpent,
                    note = this.note,
                    isCompleted = this.isCompleted,
                    createdBy = user
            )

    private fun Job.toJobForListResponse()=
            JobGetForListResponse(
                    id = this.id,
                    subject = this.subject,
                    companyName = this.companyName,
                    name = this.name,
                    surname = this.surname,
                    street = this.street,
                    city = this.city,
                    isCompleted = this.isCompleted
            )

    private fun Job.toJobResponse()=
            JobGetAllResponse(
                    id = this.id,
                    companyName = this.companyName,
                    name = this.name,
                    surname = this.surname,
                    street = this.street,
                    postalCode = this.postalCode,
                    city = this.city,
                    phoneNumber = this.phoneNumber,
                    email = this.email,
                    subject = this.subject,
                    jobType = this.jobType,
                    dateOfCreation = this.dateOfCreation,
                    plannedDate = this.plannedDate,
                    timeSpent = this.timeSpent,
                    note = this.note,
                    isCompleted = this.isCompleted,
                    createdBy = UserResponse(
                            username = this.createdBy.username,
                            name = this.createdBy.name,
                            surname = this.createdBy.surname
                    ),
                    jobAppliedTo = this.jobAppliedTo?: listOf()
            )

    override fun updateJob(request: JobRequestUpdate): Single<JobResponse> =
            jobRepository.findById(request.objectId).toSingle().flatMap { saveJob(it.copy(
                    companyName = request.companyName,
                    name = request.name,
                    surname = request.surname,
                    street = request.street,
                    postalCode = request.postalCode,
                    city = request.city,
                    phoneNumber = request.phoneNumber,
                    email = request.email,
                    subject =  request.subject,
                    jobType = request.jobType,
                    plannedDate = request.plannedDate,
                    timeSpent = request.timeSpent,
                    note = request.note,
                    isCompleted = request.isCompleted,

            )) }



}