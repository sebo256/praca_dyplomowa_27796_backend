package com.praca.dyplomowa.backend.authentication.registration.service

import com.praca.dyplomowa.backend.authentication.registration.models.RegistrationRequest
import com.praca.dyplomowa.backend.authentication.registration.models.RegistrationResponse
import io.reactivex.rxjava3.core.Single

interface IRegistrationService {

    fun registerUser(request: RegistrationRequest): Single<RegistrationResponse>

}