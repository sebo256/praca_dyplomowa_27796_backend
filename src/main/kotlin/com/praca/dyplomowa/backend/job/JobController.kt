package com.praca.dyplomowa.backend.job

import com.praca.dyplomowa.backend.job.models.*
import com.praca.dyplomowa.backend.job.usecase.IJobUseCase
import com.praca.dyplomowa.backend.mongoDb.Job
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.flowables.GroupedFlowable
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import reactor.adapter.rxjava.RxJava3Adapter.singleToMono
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/job")
class JobController(private val jobUseCase: IJobUseCase) {


    @PostMapping
    fun add(@RequestBody jobRequest: JobRequest): Single<JobResponse> =
            jobUseCase.createJob(jobRequest)

    @GetMapping
    fun getJobs(): Single<JobGetAllResponseCollection> =
            jobUseCase.getJobs()

    @GetMapping("/getJobsForList")
    fun getJobsForList(): Single<JobGetForListResponseCollection> =
            jobUseCase.getJobsForList()

    @GetMapping("/getDatesAndInfo")
    fun getJobDatesAndInfo(): Single<JobGetDatesAndInfoResponseCollection> =
            jobUseCase.getDatesAndInfo()

    @GetMapping("/getById/{objectId}")
    fun getJobById(@PathVariable objectId: String): Single<JobGetAllResponse> =
            jobUseCase.getJobById(objectId)

    @GetMapping("/getById/jobAppliedTo/{objectId}")
    fun getJobAppliedTo(@PathVariable objectId: String): Single<JobAppliedToResponse> =
            jobUseCase.getJobAppliedTo(objectId)

    @GetMapping("/getByUsername/getJobsAppliedToUserAndCheckCompletion/")
    fun getJobsAppliedToUserAndCheckCompleted(@RequestParam username: String,@RequestParam isCompleted: Boolean): Single<JobGetForListResponseCollection> =
            jobUseCase.getJobsAppliedToUserAndCheckCompleted(username, isCompleted)

    @GetMapping("/getByUsername/countJobsAppliedToUserAndCheckCompletion/")
    fun countJobsAppliedToUserAndCheckCompleted(@RequestParam username: String,@RequestParam isCompleted: Boolean): Single<Long> =
            jobUseCase.countJobsAppliedToUserAndCheckCompleted(username, isCompleted)

    @GetMapping("/getByLongDateBetween/")
    fun getJobByLongDateBetween(@RequestParam startLong: Long, @RequestParam endLong: Long): Single<JobGetAllResponseCollection> =
            jobUseCase.getJobByLongDateBetween(startLong, endLong)

    @GetMapping("/getSumOfTimeSpentForSpecifiedMonthAndUserAndCheckCompletion/")
    fun getSumOfTimeSpentForSpecifiedMonthAndUserAndCheckCompleted(@RequestParam startLong: Long, @RequestParam endLong: Long, @RequestParam username: String, @RequestParam isCompleted: Boolean): Single<Int> =
            jobUseCase.getSumOfTimeSpentForSpecifiedMonthAndUserAndCheckCompleted(startLong, endLong, username, isCompleted)

    @GetMapping("/getFirstCompletedJobDateLongForUser/")
    fun getFirstCompletedJobDateLongForUser(@RequestParam username: String): Single<JobPlannedDateResponse> =
            jobUseCase.getFirstCompletedJobDateLongForUser(username)

    @GetMapping("/getAllTimeSpentForUserPerMonth/")
    fun getAllTimeSpentForUserPerMonth(@RequestParam username: String): Single<JobTimeSpentResponseMap> =
            jobUseCase.getAllTimeSpentForUserPerMonth(username)

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