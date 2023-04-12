package com.praca.dyplomowa.backend.authentication.registration

import com.praca.dyplomowa.backend.authentication.registration.models.RegistrationRequest
import com.praca.dyplomowa.backend.authentication.registration.models.RegistrationResponse
import com.praca.dyplomowa.backend.authentication.registration.service.IRegistrationService
import io.reactivex.rxjava3.core.Single
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/auth")
class RegistrationController(private val registrationService: IRegistrationService) {

    @PostMapping("/register")
    fun register(@RequestBody registerRequest: RegistrationRequest): Single<RegistrationResponse> =
            registrationService.registerUser(registerRequest)

}