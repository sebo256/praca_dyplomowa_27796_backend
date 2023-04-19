package com.praca.dyplomowa.backend.mongoDb.repository

import com.praca.dyplomowa.backend.mongoDb.Client
import org.springframework.data.repository.reactive.RxJava3CrudRepository
import org.springframework.data.repository.reactive.RxJava3SortingRepository

interface ClientRepository: RxJava3CrudRepository<Client, String>, RxJava3SortingRepository<Client, String> {
}