package com.praca.dyplomowa.backend.job.usecase

import com.praca.dyplomowa.backend.job.models.*
import com.praca.dyplomowa.backend.mongoDb.Job
import com.praca.dyplomowa.backend.mongoDb.User
import com.praca.dyplomowa.backend.mongoDb.repository.JobRepository
import com.praca.dyplomowa.backend.mongoDb.repository.UserRepository
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.kotlin.subscribeBy
import org.springframework.data.domain.Sort
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class JobUseCase(
        private val userRepository: UserRepository,
        private val jobRepository: JobRepository
):IJobUseCase {
//
//    @Autowired
//    lateinit var jobRepository: JobRepository

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

    override fun getJobById(objectId: String): Single<JobGetAllResponse> =
            jobRepository.findById(objectId).toSingle().map { it.toJobResponse() }

    override fun getJobAppliedTo(objectId: String): Single<JobAppliedToResponse> =
            jobRepository.findById(objectId).toSingle().map { it.toGetJobAppliedToResponse() }


    override fun deleteJob(objectId: String): Single<JobResponse> =
            jobRepository.existsById(objectId).flatMap { status ->
                when(status){
                    true -> jobRepository.deleteById(objectId).toSingleDefault(deleteResponse())
                    false -> Single.error(ResponseStatusException(HttpStatus.BAD_REQUEST))
                }
            }

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