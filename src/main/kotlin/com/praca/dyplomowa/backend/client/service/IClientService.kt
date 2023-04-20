package com.praca.dyplomowa.backend.client.service

import com.praca.dyplomowa.backend.client.models.*
import io.reactivex.rxjava3.core.Single

interface IClientService {

    fun addClient(clientRequest: ClientRequest): Single<ClientResponse>

    fun getClients(): Single<ClientGetAllResponseCollection>

    fun getClientById(objectId: String): Single<ClientGetAllResponse>

    fun updateClient(clientRequestUpdate: ClientRequestUpdate): Single<ClientResponse>

}