package com.praca.dyplomowa.backend.mongoDb.repository

import com.praca.dyplomowa.backend.mongoDb.Job
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single
import org.springframework.data.repository.reactive.RxJava3CrudRepository
import org.springframework.data.repository.reactive.RxJava3SortingRepository

interface JobRepository: RxJava3CrudRepository<Job, String>, RxJava3SortingRepository<Job, String> {

    fun findAllByPlannedDateBetween(startLong: Long, endLong: Long): Flowable<Job>

    fun findAllByPlannedDateBetweenAndJobAppliedToContainingAndIsCompleted(startLong: Long, endLong: Long, jobAppliedTo: String, isCompleted: Boolean): Flowable<Job>

    fun findAllByJobAppliedToContainingAndIsCompleted(jobAppliedTo: String, isCompleted: Boolean): Flowable<Job>

    fun countAllByJobAppliedToContainingAndIsCompleted(jobAppliedTo: String, isCompleted: Boolean): Single<Long>

    fun findAllByJobAppliedToAndIsCompletedOrderByPlannedDateAsc(username: String, isCompleted: Boolean): Flowable<Job>

}