package com.praca.dyplomowa.backend.me.usecase

import com.praca.dyplomowa.backend.me.models.MeRequest
import com.praca.dyplomowa.backend.me.models.MeResponse

interface IMeUsecase {

    fun getBirthYear(request: MeRequest): MeResponse

}