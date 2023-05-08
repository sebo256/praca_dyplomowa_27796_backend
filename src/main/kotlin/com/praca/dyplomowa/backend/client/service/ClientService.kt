package com.praca.dyplomowa.backend.client.service

import com.praca.dyplomowa.backend.client.models.*
import com.praca.dyplomowa.backend.logger.IApplicationLogger
import com.praca.dyplomowa.backend.mongoDb.Client
import com.praca.dyplomowa.backend.mongoDb.repository.ClientRepository
import com.praca.dyplomowa.backend.mongoDb.repository.JobRepository
import io.reactivex.rxjava3.core.Single
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service

@Service
class ClientService(
        private val clientRepository: ClientRepository,
        private val jobRepository: JobRepository,
        private val logger: IApplicationLogger
): IClientService {

    override fun addClient(clientRequest: ClientRequest): Single<ClientResponse> =
            saveClient(clientRequest.toClient()).map { it.toNewClientResponse() }
                    .doOnSuccess {
                        logger.info("Succesfully created client with id: ${it.id}")
                    }
                    .onErrorReturn {
                        errorResponse()
                    }

    private fun saveClient(client: Client) =
            clientRepository.save(client)

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
            }.map {
                it.toUpdateClientResponse()
            }
            .doOnSuccess {
                logger.info("Succesfully modified client with id: ${it.id}")
            }
            .onErrorReturn {
                errorResponse()
            }

    override fun deleteClient(objectId: String): Single<ClientResponse> =
            clientRepository.findById(objectId).toSingle().flatMap {
                        jobRepository.countAllByClient(it).flatMap {
                            when(it < 1){
                                true -> clientRepository.deleteById(objectId).toSingleDefault(deleteResponse())
                                false -> Single.fromCallable{ errorResponse() }
                            }
                        }
                    }.doOnSuccess {
                when(it.status){
                    true -> logger.info("Succesfully deleted client")
                    false -> logger.info("Trying to delete applied client")
                }
            }

    private fun errorResponse() =
            ClientResponse(
                    status = false,
                    message = "Something went wrong in operation on Client"
            )

    private fun deleteResponse() =
            ClientResponse(
                    status = true,
                    message = "Successfully deleted client"
            )

    private fun Client.toNewClientResponse() =
            ClientResponse(
                    id = this.id,
                    status = true,
                    message = "Succesfully created new client"
            )

    private fun Client.toUpdateClientResponse() =
            ClientResponse(
                    id = this.id,
                    status = true,
                    message = "Succesfully updated client"
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