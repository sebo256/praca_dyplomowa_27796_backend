package com.praca.dyplomowa.backend.authentication.registration

import com.praca.dyplomowa.backend.authentication.registration.models.RegistrationRequest
import com.praca.dyplomowa.backend.authentication.registration.models.RegistrationResponse
import com.praca.dyplomowa.backend.authentication.registration.service.IRegistrationService
import com.praca.dyplomowa.backend.logger.IApplicationLogger
import io.reactivex.rxjava3.core.Single
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import reactor.core.publisher.Mono
import reactor.kotlin.core.publisher.toMono


@RestController
@RequestMapping("/auth")
class RegistrationController(private val registrationService: IRegistrationService,  val logger: IApplicationLogger) {

    @PostMapping("/register")
    fun register(@RequestBody registerRequest: RegistrationRequest): Single<RegistrationResponse> =
            registrationService.registerUser(registerRequest)

    @ExceptionHandler(IllegalStateException::class)
    fun illeaglStateException(exc: IllegalStateException): Mono<RegistrationResponse> {
        logger.error("RegistrationController " + exc.toString())
        return ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR).toMono()
    }

}