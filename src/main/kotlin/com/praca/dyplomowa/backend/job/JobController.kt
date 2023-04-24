package com.praca.dyplomowa.backend.job

import com.praca.dyplomowa.backend.client.models.ClientResponse
import com.praca.dyplomowa.backend.job.models.*
import com.praca.dyplomowa.backend.job.service.IJobService
import com.praca.dyplomowa.backend.logger.IApplicationLogger
import io.reactivex.rxjava3.core.Single
import org.springframework.http.HttpStatus
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import reactor.adapter.rxjava.RxJava3Adapter.singleToMono
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono

@RestController
@RequestMapping("/job")
class JobController(private val jobService: IJobService, val logger: IApplicationLogger) {


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

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/getById/jobAppliedTo/{objectId}")
    fun getJobAppliedTo(@PathVariable objectId: String): Mono<JobAppliedToResponse> =
            singleToMono(jobService.getJobAppliedTo(objectId))

    @GetMapping("/getByUsername/getJobsAppliedToUserAndCheckCompletion/")
    fun getJobsAppliedToUserAndCheckCompleted(@RequestParam username: String,@RequestParam isCompleted: Boolean): Single<JobGetForListResponseCollection> =
            jobService.getJobsAppliedToUserAndCheckCompleted(username, isCompleted)

    @GetMapping("/getByUsername/countJobsAppliedToUserAndCheckCompletion/")
    fun countJobsAppliedToUserAndCheckCompleted(@RequestParam username: String,@RequestParam isCompleted: Boolean): Single<Long> =
            jobService.countJobsAppliedToUserAndCheckCompleted(username, isCompleted)

    @GetMapping("/getByLongDateBetween/")
    fun getJobByLongDateBetween(@RequestParam startLong: Long, @RequestParam endLong: Long): Single<JobGetForListResponseCollection> =
            jobService.getJobByLongDateBetween(startLong, endLong)

    @GetMapping("/getSumOfTimeSpentForSpecifiedMonthAndUser/")
    fun getSumOfTimeSpentForSpecifiedMonthAndUser(@RequestParam startLong: Long, @RequestParam endLong: Long, @RequestParam username: String): Single<Int> =
            jobService.getSumOfTimeSpentForSpecifiedMonthAndUser(startLong, endLong, username)

    @GetMapping("/getJobsForSpecifiedMonthAndUser/")
    fun getJobsForSpecifiedMonthAndUser(@RequestParam startLong: Long, @RequestParam endLong: Long, @RequestParam username: String): Single<JobGetForListHoursResponseCollection> =
            jobService.getJobsForSpecifiedMonthAndUser(startLong, endLong, username)

    @GetMapping("/getAllTimeSpentForUserPerMonth/")
    fun getAllTimeSpentForUserPerMonth(@RequestParam username: String): Single<JobTimeSpentResponseCollection> =
            jobService.getAllTimeSpentForUserPerMonth(username)

    @PutMapping("/addJobApplyTo")
    fun addJobApplyTo(@RequestBody jobApplyToRequest: JobApplyToRequest): Single<JobResponse> =
            jobService.addJobApplyTo(jobApplyToRequest)

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/addTime")
    fun addTimeSpent(@RequestBody jobAddTimeSpentRequest: JobAddTimeSpentRequest): Mono<JobResponse> =
            singleToMono(jobService.addTimeSpent(jobAddTimeSpentRequest))

    @PutMapping
    fun updateJob(@RequestBody jobRequestUpdate: JobRequestUpdate): Single<JobResponse> =
            jobService.updateJob(jobRequestUpdate)

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("{objectId}")
    fun deleteJob(@PathVariable objectId: String): Mono<JobResponse> =
            singleToMono(jobService.deleteJob(objectId))

    @ExceptionHandler(IllegalStateException::class)
    fun illeaglStateException(exc: IllegalStateException): Mono<JobResponse> {
        logger.error("JobController " + exc.toString())
        return ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR).toMono()
    }

    @ExceptionHandler(NoSuchElementException::class)
    fun noSuchElementExceptionJob(exc: NoSuchElementException): Mono<JobResponse> {
        logger.error("JobController" + exc.toString())
        return ResponseStatusException(HttpStatus.NOT_FOUND).toMono()
    }

}