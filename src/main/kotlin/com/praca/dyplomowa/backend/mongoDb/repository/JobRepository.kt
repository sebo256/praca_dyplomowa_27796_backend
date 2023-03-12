package com.praca.dyplomowa.backend.mongoDb.repository

import com.praca.dyplomowa.backend.mongoDb.Job
import com.praca.dyplomowa.backend.mongoDb.User
import io.reactivex.rxjava3.core.Single
import org.bson.types.ObjectId
import org.springframework.data.repository.reactive.RxJava3CrudRepository

interface JobRepository: RxJava3CrudRepository<Job, String> {


}