package com.praca.dyplomowa.backend.mongoDb.repository

import com.praca.dyplomowa.backend.mongoDb.Job
import org.springframework.data.repository.reactive.RxJava3CrudRepository

interface JobRepository: RxJava3CrudRepository<Job, String> {



}