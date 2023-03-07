package com.praca.dyplomowa.backend.me

import com.praca.dyplomowa.backend.me.models.MeRequest
import com.praca.dyplomowa.backend.me.models.MeResponse
import com.praca.dyplomowa.backend.me.usecase.IMeUsecase
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class MeController(val meUsecase: IMeUsecase) {

    @PostMapping("/me")
    fun me(@RequestBody request: MeRequest): MeResponse =
            meUsecase.getBirthYear(request)

}