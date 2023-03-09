package com.praca.dyplomowa.backend.job.usecase

import com.praca.dyplomowa.backend.job.models.JobRequest
import com.praca.dyplomowa.backend.job.models.JobResponse
import com.praca.dyplomowa.backend.mongoDb.Job
import com.praca.dyplomowa.backend.mongoDb.User
import com.praca.dyplomowa.backend.mongoDb.repository.JobRepository
import com.praca.dyplomowa.backend.mongoDb.repository.UserRepository
import io.reactivex.rxjava3.core.Single
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class JobUseCase(
        private val userRepository: UserRepository
):IJobUseCase {

    @Autowired
    lateinit var jobRepository: JobRepository

    override fun createJob(request: JobRequest): Single<JobResponse> =
            userRepository.findByUsername(request.createdBy!!).flatMap { saveJob(request, it) }
                    .onErrorReturn { errorResponse() }

    private fun saveJob(request: JobRequest, user: User) =
            jobRepository.save(request.toJob(user)).map { it.toNewJobResponse() }

    private fun errorResponse() =
            JobResponse(
                    status = false,
                    message = "Something went wrong"
            )

    private fun Job.toNewJobResponse() =
            JobResponse(
                    status = true,
                    message = "Succesfully created new job"
            )

    fun JobRequest.toJob(user: User) =
            Job(
                    companyName = this.companyName,
                    name = this.name,
                    surname = this.surname,
                    street = this.street,
                    postalCode = this.postalCode,
                    city = this.city,
                    phoneNumber = this.phoneNumber,
                    email = this.email,
                    dateOfCreation = this.dateOfCreation,
                    plannedDate = this.plannedDate,
                    timeSpent = this.timeSpent,
                    note = this.note,
                    isCompleted = this.isCompleted,
                    createdBy = listOf( user )
            )
}