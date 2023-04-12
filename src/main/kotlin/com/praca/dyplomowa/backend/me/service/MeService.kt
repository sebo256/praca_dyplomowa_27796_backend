package com.praca.dyplomowa.backend.me.service

import com.praca.dyplomowa.backend.me.models.MeRequest
import com.praca.dyplomowa.backend.me.models.MeResponse
import org.springframework.stereotype.Service
import java.util.*

@Service
class MeService: IMeService {
    override fun getBirthYear(request: MeRequest) =
            MeResponse(
                    username = request.username,
                    birthYear = calculateYear(request.age),
            )

    private fun calculateYear(age: Int): Int =
        Date(System.currentTimeMillis()).year - age

}