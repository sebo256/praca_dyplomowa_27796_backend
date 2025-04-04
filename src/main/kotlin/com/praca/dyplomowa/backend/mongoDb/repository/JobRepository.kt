package com.praca.dyplomowa.backend.mongoDb.repository

import com.praca.dyplomowa.backend.mongoDb.Client
import com.praca.dyplomowa.backend.mongoDb.Job
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single
import org.springframework.data.repository.reactive.RxJava3CrudRepository
import org.springframework.data.repository.reactive.RxJava3SortingRepository

interface JobRepository: RxJava3CrudRepository<Job, String>, RxJava3SortingRepository<Job, String> {

    fun findAllByPlannedDateBetween(startLong: Long, endLong: Long): Flowable<Job>

    fun findAllByPlannedDateBetweenAndJobAppliedToContaining(startLong: Long, endLong: Long, jobAppliedTo: String): Flowable<Job>

    fun findAllByJobAppliedToContainingAndIsCompleted(jobAppliedTo: String, isCompleted: Boolean): Flowable<Job>

    fun countAllByJobAppliedToContainingAndIsCompleted(jobAppliedTo: String, isCompleted: Boolean): Single<Long>

    fun findAllByJobAppliedToContainingOrderByPlannedDateAsc(username: String): Flowable<Job>

    fun countAllByClient(client: Client): Single<Long>
}