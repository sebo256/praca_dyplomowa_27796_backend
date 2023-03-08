package com.praca.dyplomowa.backend.security.jwtUtils

import javax.naming.AuthenticationException

class InvalidBearerToken(message: String?) : AuthenticationException(message) {
}