package com.praca.dyplomowa.backend.authentication.registration.usecase

import com.praca.dyplomowa.backend.authentication.registration.models.RegistrationRequest
import com.praca.dyplomowa.backend.authentication.registration.models.RegistrationResponse
import com.praca.dyplomowa.backend.authentication.registration.models.RegistrationSuccess
import com.praca.dyplomowa.backend.logger.IApplicationLogger
import com.praca.dyplomowa.backend.mongoDb.ERoles
import com.praca.dyplomowa.backend.mongoDb.User
import com.praca.dyplomowa.backend.mongoDb.repository.UserRepository
import com.praca.dyplomowa.backend.security.SecurityConfig
import io.reactivex.rxjava3.core.Single
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class RegistrationUseCase(val logger: IApplicationLogger): IRegistrationUseCase {

    @Autowired
    lateinit var userRepository: UserRepository

    override fun registerUser(request: RegistrationRequest): Single<RegistrationResponse> {
        val validationStatus = ValidationRegistrationData(request).validate()
        return when(validationStatus.status){
            true -> addUser(request)
            false -> registrationResponseInvalidData(validationStatus)
        }
    }

    private fun addUser(data: RegistrationRequest): Single<RegistrationResponse> =
            userRepository.save(data.toUser()).map {
                logger.info("Succesfully registered account with id: ${it.id}")
                it.toRegistrationResponse()
            }
            .onErrorReturn {
                logger.warn("Registration failed. User with given username already exist $it")
                    RegistrationResponse(
                            status = false,
                            account = null,
                            message = "User with given username already exist"
                    )
            }

    private fun User.toRegistrationResponse() =
            RegistrationResponse(
                    status = true,
                    account = RegistrationSuccess(
                            id = this.id,
                            username = this.username
                    ),
                    message = "Sucessfully registered account"
            )

    private fun registrationResponseInvalidData(validation: Validation) =
            Single.fromCallable{
                RegistrationResponse(
                        status = validation.status,
                        account = null,
                        message = validation.messages.joinToString(";")
                )
            }

    private fun RegistrationRequest.toUser() =
            User(
                    username = this.username,
                    password = SecurityConfig().passwordEncoder().encode(this.password),
                    name = this.name,
                    surname = this.surname,
                    roles = listOf(ERoles.ROLE_USER.toString())
            )
}