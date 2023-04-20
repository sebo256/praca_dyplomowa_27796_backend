package com.praca.dyplomowa.backend.client

import com.praca.dyplomowa.backend.client.models.*
import com.praca.dyplomowa.backend.client.service.IClientService
import io.reactivex.rxjava3.core.Single
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

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

}