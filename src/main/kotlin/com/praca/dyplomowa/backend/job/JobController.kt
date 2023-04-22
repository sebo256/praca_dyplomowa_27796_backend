package com.praca.dyplomowa.backend.job

import com.praca.dyplomowa.backend.job.models.*
import com.praca.dyplomowa.backend.job.service.IJobService
import io.reactivex.rxjava3.core.Single
import org.springframework.http.HttpStatus
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import reactor.adapter.rxjava.RxJava3Adapter.singleToMono
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono
import java.util.NoSuchElementException

@RestController
@RequestMapping("/job")
class JobController(private val jobService: IJobService) {


    @PostMapping
    fun add(@RequestBody jobRequest: JobRequest): Single<JobResponse> =
            jobService.createJob(jobRequest)

    @GetMapping
    fun getJobs(): Single<JobGetAllResponseCollection> =
            jobService.getJobs()

    @GetMapping("/getJobsForList")
    fun getJobsForList(): Single<JobGetForListResponseCollection> =
            jobService.getJobsForList()

    @GetMapping("/getDatesAndInfo")
    fun getJobDatesAndInfo(): Single<JobGetDatesAndInfoResponseCollection> =
            jobService.getDatesAndInfo()

    @GetMapping("/getById/{objectId}")
    fun getJobById(@PathVariable objectId: String): Single<JobGetAllResponse> =
            jobService.getJobById(objectId)

    @GetMapping("/getById/jobAppliedTo/{objectId}")
    fun getJobAppliedTo(@PathVariable objectId: String): Single<JobAppliedToResponse> =
            jobService.getJobAppliedTo(objectId)

    @GetMapping("/getByUsername/getJobsAppliedToUserAndCheckCompletion/")
    fun getJobsAppliedToUserAndCheckCompleted(@RequestParam username: String,@RequestParam isCompleted: Boolean): Single<JobGetForListResponseCollection> =
            jobService.getJobsAppliedToUserAndCheckCompleted(username, isCompleted)

    @GetMapping("/getByUsername/countJobsAppliedToUserAndCheckCompletion/")
    fun countJobsAppliedToUserAndCheckCompleted(@RequestParam username: String,@RequestParam isCompleted: Boolean): Single<Long> =
            jobService.countJobsAppliedToUserAndCheckCompleted(username, isCompleted)

    @GetMapping("/getByLongDateBetween/")
    fun getJobByLongDateBetween(@RequestParam startLong: Long, @RequestParam endLong: Long): Single<JobGetAllResponseCollection> =
            jobService.getJobByLongDateBetween(startLong, endLong)

    @GetMapping("/getSumOfTimeSpentForSpecifiedMonthAndUserAndCheckCompletion/")
    fun getSumOfTimeSpentForSpecifiedMonthAndUserAndCheckCompleted(@RequestParam startLong: Long, @RequestParam endLong: Long, @RequestParam username: String, @RequestParam isCompleted: Boolean): Single<Int> =
            jobService.getSumOfTimeSpentForSpecifiedMonthAndUserAndCheckCompleted(startLong, endLong, username, isCompleted)

    @GetMapping("/getJobsForSpecifiedMonthAndUserAndCheckCompleted/")
    fun getJobsForSpecifiedMonthAndUserAndCheckCompleted(@RequestParam startLong: Long, @RequestParam endLong: Long, @RequestParam username: String): Single<JobGetForListResponseCollection> =
            jobService.getJobsForSpecifiedMonthAndUserAndCheckCompleted(startLong, endLong, username)

    @GetMapping("/getAllTimeSpentForUserPerMonth/")
    fun getAllTimeSpentForUserPerMonth(@RequestParam username: String): Single<JobTimeSpentResponseCollection> =
            jobService.getAllTimeSpentForUserPerMonth(username)

    @PutMapping("/addJobApplyTo")
    fun addJobApplyTo(@RequestBody jobApplyToRequest: JobApplyToRequest): Single<JobResponse> =
            jobService.addJobApplyTo(jobApplyToRequest)

    @PutMapping
    fun updateJob(@RequestBody jobRequestUpdate: JobRequestUpdate): Single<JobResponse> =
            jobService.updateJob(jobRequestUpdate)

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("{objectId}")
    fun deleteJob(@PathVariable objectId: String): Mono<JobResponse> =
            singleToMono(jobService.deleteJob(objectId))

    @ExceptionHandler(NoSuchElementException::class)
    fun noSuchElementExceptionJob(): Mono<JobResponse> =
            ResponseStatusException(HttpStatus.NOT_FOUND).toMono()
}