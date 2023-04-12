package com.praca.dyplomowa.backend.me.service

import com.praca.dyplomowa.backend.me.models.MeRequest
import com.praca.dyplomowa.backend.me.models.MeResponse

interface IMeService {

    fun getBirthYear(request: MeRequest): MeResponse

}