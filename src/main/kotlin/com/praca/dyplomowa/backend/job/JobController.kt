package com.praca.dyplomowa.backend.job

import com.praca.dyplomowa.backend.job.models.*
import com.praca.dyplomowa.backend.job.usecase.IJobUseCase
import io.reactivex.rxjava3.core.Single
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import reactor.adapter.rxjava.RxJava3Adapter.singleToMono
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono

@RestController
@RequestMapping("/job")
class JobController(private val jobUseCase: IJobUseCase) {


    @PostMapping
    fun add(@RequestBody jobRequest: JobRequest): Single<JobResponse> =
            jobUseCase.createJob(jobRequest)

    @GetMapping
    fun getJobs(): Single<JobGetAllResponseCollection> =
            jobUseCase.getJobs()

    @GetMapping("/getById/{objectId}")
    fun getJobById(@PathVariable objectId: String): Single<JobGetAllResponse> =
            jobUseCase.getJobById(objectId)

    @GetMapping("/getById/jobAppliedTo/{objectId}")
    fun getJobAppliedTo(@PathVariable objectId: String): Single<JobAppliedToResponse> =
            jobUseCase.getJobAppliedTo(objectId)

    @PutMapping("/addJobApplyTo")
    fun addJobApplyTo(@RequestBody jobApplyToRequest: JobApplyToRequest): Single<JobResponse> =
            jobUseCase.addJobApplyTo(jobApplyToRequest)

    @PutMapping
    fun updateJob(@RequestBody jobRequestUpdate: JobRequestUpdate): Single<JobResponse> =
            jobUseCase.updateJob(jobRequestUpdate)

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("{objectId}")
    fun deleteJob(@PathVariable objectId: String): Mono<JobResponse> =
            singleToMono(jobUseCase.deleteJob(objectId))

}