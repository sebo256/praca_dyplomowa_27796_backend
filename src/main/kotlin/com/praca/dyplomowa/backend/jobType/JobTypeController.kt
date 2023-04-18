package com.praca.dyplomowa.backend.jobType

import com.praca.dyplomowa.backend.jobType.models.JobTypeGetAllResponseCollection
import com.praca.dyplomowa.backend.jobType.models.JobTypeRequest
import com.praca.dyplomowa.backend.jobType.models.JobTypeResponse
import com.praca.dyplomowa.backend.jobType.service.IJobTypeService
import io.reactivex.rxjava3.core.Single
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/jobType")
class JobTypeController(private val jobTypeService: IJobTypeService) {

    @PostMapping
    fun addJobType(@RequestBody jobTypeRequest: JobTypeRequest): Single<JobTypeResponse> =
            jobTypeService.addJobType(jobTypeRequest)

    @GetMapping
    fun getJobTypes(): Single<JobTypeGetAllResponseCollection> =
            jobTypeService.getJobTypes()

}