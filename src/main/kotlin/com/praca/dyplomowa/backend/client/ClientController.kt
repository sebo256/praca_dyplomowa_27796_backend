package com.praca.dyplomowa.backend.client

import com.praca.dyplomowa.backend.client.models.ClientGetAllResponseCollection
import com.praca.dyplomowa.backend.client.models.ClientRequest
import com.praca.dyplomowa.backend.client.models.ClientResponse
import com.praca.dyplomowa.backend.client.service.IClientService
import io.reactivex.rxjava3.core.Single
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
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

}