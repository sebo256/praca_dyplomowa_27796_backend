package com.praca.dyplomowa.backend.jobType.service

import com.praca.dyplomowa.backend.job.models.JobGetAllResponseCollection
import com.praca.dyplomowa.backend.jobType.models.JobTypeGetAllResponse
import com.praca.dyplomowa.backend.jobType.models.JobTypeGetAllResponseCollection
import com.praca.dyplomowa.backend.jobType.models.JobTypeRequest
import com.praca.dyplomowa.backend.jobType.models.JobTypeResponse
import com.praca.dyplomowa.backend.mongoDb.JobType
import com.praca.dyplomowa.backend.mongoDb.repository.JobTypeRepository
import io.reactivex.rxjava3.core.Single
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service

@Service
class JobTypeService(
        private val jobTypeRepository: JobTypeRepository
):IJobTypeService {

    override fun addJobType(jobTypeRequest: JobTypeRequest): Single<JobTypeResponse> =
            saveJobType(jobTypeRequest.toJobType())
                .onErrorReturn { errorResponse() }

    private fun saveJobType(jobType: JobType) =
            jobTypeRepository.save(jobType).map { it.toNewJobTypeResponse() }

    override fun getJobTypes(): Single<JobTypeGetAllResponseCollection> =
            jobTypeRepository.findAll(Sort.by(Sort.Direction.ASC, "jobType")).toList().map {
                JobTypeGetAllResponseCollection(
                        it.map {
                            it.toJobGetAllResponse()
                        }
                )
            }


    private fun errorResponse() =
            JobTypeResponse(
                    status = false,
                    message = "Something went wrong in creating new job type"
            )

    private fun JobType.toNewJobTypeResponse() =
            JobTypeResponse(
                    id = this.id,
                    status = true,
                    message = "Succesfully created new job type"
            )

    private fun JobType.toJobGetAllResponse() =
            JobTypeGetAllResponse(
                    id = this.id,
                    jobType = this.jobType
            )

    private fun JobTypeRequest.toJobType() =
            JobType(
                    jobType = this.jobType
            )

}