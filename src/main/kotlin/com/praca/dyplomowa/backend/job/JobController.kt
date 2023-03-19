package com.praca.dyplomowa.backend.job

import com.praca.dyplomowa.backend.job.models.*
import com.praca.dyplomowa.backend.job.usecase.IJobUseCase
import io.reactivex.rxjava3.core.Single
import org.springframework.context.annotation.Role
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/job")
class JobController(private val jobUseCase: IJobUseCase) {


    @PostMapping
    fun add(@RequestBody jobRequest: JobRequest): Single<JobResponse> =
            jobUseCase.createJob(jobRequest)

    @PostMapping("/addJobAppliedTo")
    fun addJobAppliedTo(@RequestBody jobApplyToRequest: JobApplyToRequest): Single<JobResponse> =
            jobUseCase.addJobAppliedTo(jobApplyToRequest)

    @GetMapping
    fun getJobs(): Single<JobGetAllResponseCollection> =
            jobUseCase.getJobs()

    @PutMapping
    fun updateJob(@RequestBody jobRequestUpdate: JobRequestUpdate): Single<JobResponse> =
            jobUseCase.updateJob(jobRequestUpdate)


}