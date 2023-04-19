package com.praca.dyplomowa.backend.client.service

import com.praca.dyplomowa.backend.client.models.ClientGetAllResponseCollection
import com.praca.dyplomowa.backend.client.models.ClientRequest
import com.praca.dyplomowa.backend.client.models.ClientResponse
import io.reactivex.rxjava3.core.Single

interface IClientService {

    fun addClient(clientRequest: ClientRequest): Single<ClientResponse>

    fun getClients(): Single<ClientGetAllResponseCollection>

}