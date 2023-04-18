package com.praca.dyplomowa.backend.mongoDb.repository

import com.praca.dyplomowa.backend.mongoDb.Job
import com.praca.dyplomowa.backend.mongoDb.JobType
import org.springframework.data.repository.reactive.RxJava3CrudRepository
import org.springframework.data.repository.reactive.RxJava3SortingRepository

interface JobTypeRepository: RxJava3CrudRepository<JobType, String>, RxJava3SortingRepository<JobType, String> {



}