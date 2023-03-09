package com.praca.dyplomowa.backend.job

import com.praca.dyplomowa.backend.job.models.JobRequest
import com.praca.dyplomowa.backend.job.models.JobResponse
import com.praca.dyplomowa.backend.job.usecase.IJobUseCase
import io.reactivex.rxjava3.core.Single
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/job")
class JobController(private val jobUseCase: IJobUseCase) {

    @PostMapping("/add")
    fun add(@RequestBody jobRequest: JobRequest): Single<JobResponse> =
            jobUseCase.createJob(jobRequest)

    //TODO Dodać drugi endpoint który będzie dodawał użytkowników do listy. Wygląda to mniej więcej tak, Najpierw dodajemy najważniejsze dane, a później na kolejnym ekranie wybieramy pracowników którzy będą wykonywać to zlecenie
}