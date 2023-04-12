package com.praca.dyplomowa.backend.me

import com.praca.dyplomowa.backend.me.models.MeRequest
import com.praca.dyplomowa.backend.me.models.MeResponse
import com.praca.dyplomowa.backend.me.service.IMeService
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
class MeController(val meService: IMeService) {
    //TODO Wyrzucić póżniej, jak będę przygotowywać pliki do wysyłania, albo i wcześniej
    @PreAuthorize("hasRole('USER')")
    @PostMapping("/me")
    fun me(@RequestBody request: MeRequest): Mono<MeResponse> =
            Mono.just(meService.getBirthYear(request))

}