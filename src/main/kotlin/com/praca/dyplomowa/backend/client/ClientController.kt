package com.praca.dyplomowa.backend.client

import com.praca.dyplomowa.backend.client.models.*
import com.praca.dyplomowa.backend.client.service.IClientService
import io.reactivex.rxjava3.core.Single
import org.springframework.http.HttpStatus
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import reactor.adapter.rxjava.RxJava3Adapter.singleToMono
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono

@RestController
@RequestMapping("/client")
class ClientController(private val clientService: IClientService) {

    @PostMapping
    fun addClient(@RequestBody clientRequest: ClientRequest): Single<ClientResponse> =
            clientService.addClient(clientRequest)

    @GetMapping
    fun getClients(): Single<ClientGetAllResponseCollection> =
            clientService.getClients()

    @GetMapping("/getById/{objectId}")
    fun getClientById(@PathVariable objectId: String): Single<ClientGetAllResponse> =
            clientService.getClientById(objectId)

    @PutMapping
    fun updateClient(@RequestBody clientRequestUpdate: ClientRequestUpdate): Single<ClientResponse> =
            clientService.updateClient(clientRequestUpdate)

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("{objectId}")
    fun deleteClient(@PathVariable objectId: String): Mono<ClientResponse> =
            singleToMono(clientService.deleteClient(objectId))

    @ExceptionHandler(NoSuchElementException::class)
    fun noSuchElementExceptionClient(): Mono<ClientResponse> =
            ResponseStatusException(HttpStatus.NOT_FOUND).toMono()
}