package com.praca.dyplomowa.backend.client.service

import com.praca.dyplomowa.backend.client.models.*
import com.praca.dyplomowa.backend.mongoDb.Client
import com.praca.dyplomowa.backend.mongoDb.repository.ClientRepository
import io.reactivex.rxjava3.core.Single
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service

@Service
class ClientService(
        private val clientRepository: ClientRepository
): IClientService {

    override fun addClient(clientRequest: ClientRequest): Single<ClientResponse> =
            saveClient(clientRequest.toClient())
                    .onErrorReturn { errorResponse() }

    private fun saveClient(client: Client) =
            clientRepository.save(client).map { it.toNewClientResponse() }

    override fun getClients(): Single<ClientGetAllResponseCollection> =
            clientRepository.findAll(Sort.by(Sort.Direction.ASC, "name")).toList().map {
                ClientGetAllResponseCollection(
                        it.map {
                            it.toGetAllResponse()
                        }
                )
            }

    override fun getClientById(objectId: String): Single<ClientGetAllResponse> =
            clientRepository.findById(objectId).toSingle().map { it.toGetAllResponse() }

    override fun updateClient(clientRequestUpdate: ClientRequestUpdate): Single<ClientResponse> =
            clientRepository.findById(clientRequestUpdate.objectId).toSingle().flatMap {
                saveClient(it.copy(
                companyName = clientRequestUpdate.companyName,
                name = clientRequestUpdate.name,
                surname = clientRequestUpdate.surname,
                street = clientRequestUpdate.street,
                postalCode = clientRequestUpdate.postalCode,
                city = clientRequestUpdate.city,
                phoneNumber = clientRequestUpdate.phoneNumber,
                email = clientRequestUpdate.email
                ))
            }.onErrorReturn { errorResponse() }



    private fun errorResponse() =
            ClientResponse(
                    status = false,
                    message = "Something went wrong in creating new job type"
            )

    private fun Client.toNewClientResponse() =
            ClientResponse(
                    id = this.id,
                    status = true,
                    message = "Succesfully created new client"
            )

    private fun ClientRequest.toClient() =
            Client(
                    companyName = this.companyName,
                    name = this.name,
                    surname = this.surname,
                    street = this.street,
                    postalCode = this.postalCode,
                    city = this.city,
                    phoneNumber = this.phoneNumber,
                    email = this.email
            )

    private fun Client.toGetAllResponse() =
            ClientGetAllResponse(
                    id = this.id,
                    companyName = this.companyName,
                    name = this.name,
                    surname = this.surname,
                    street = this.street,
                    postalCode = this.postalCode,
                    city = this.city,
                    phoneNumber = this.phoneNumber,
                    email = this.email
            )

}