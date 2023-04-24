package com.praca.dyplomowa.backend.authentication.registration.service

import com.praca.dyplomowa.backend.authentication.registration.models.RegistrationRequest

data class Validation(
        val status: Boolean,
        val messages: List<String>
)

class ValidationRegistrationData(val data: RegistrationRequest) {
    fun validate(): Validation =
            Validation(
                    status = allValidation().filterNotNull().isEmpty(),
                    messages = allValidation().filterNotNull()
            )

    fun allValidation() =
            listOf(
                    statusMessage(validateUsername(data.username), "Wrong username"),
                    statusMessage(validatePassword(data.password), "Wrong password"),
                    statusMessage(validateNameAndSurname(data.name), "Wrong name"),
                    statusMessage(validateNameAndSurname(data.surname), "Wrong surname")
            )

    private fun validateUsername(username: String): Boolean =
            username.length in 4..20 && username.contains(USERNAME_PATTERN)

    private fun validatePassword(password: String): Boolean =
            password.length in 8..30 && password.contains(PASSWORD_PATTERN)

    private fun validateNameAndSurname(name: String): Boolean =
            name.length in 2..25 && name.contains(NAME_PATTERN)

    private fun statusMessage(status: Boolean, message: String): String? =
            when(status){
                true -> null
                false -> message
            }

    companion object{
        val PASSWORD_PATTERN = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#\$%]).{8,30}\$".toRegex()
        val USERNAME_PATTERN = "^[a-zA-Z0-9._-]{4,}\$".toRegex()
        val NAME_PATTERN = "[A-Za-zżźćńółęąśŻŹĆĄŚĘŁÓŃ]{1,25}['-]{0,2}[A-Za-zżźćńółęąśŻŹĆĄŚĘŁÓŃ]{1,25}['-]{0,2}[A-Za-zżźćńółęąśŻŹĆĄŚĘŁÓŃ]{1,25}".toRegex()
    }

}

