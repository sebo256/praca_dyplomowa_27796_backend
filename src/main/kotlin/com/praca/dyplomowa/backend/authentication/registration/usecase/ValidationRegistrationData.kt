package com.praca.dyplomowa.backend.authentication.registration.usecase

import com.praca.dyplomowa.backend.authentication.registration.models.RegistrationRequest

data class Validation(
        val status: Boolean,
        val messages: List<String>
)

class ValidationRegistrationData(val data: RegistrationRequest) {
    fun validate(): Validation =
            Validation(
                    status = status,
                    messages = messages
            )

    fun allValidation() =
            listOf(
                    statusMessage(validateUsername(data.username), "Wrong username"),
                    statusMessage(validatePassword(data.password), "Wrong password"),
                    statusMessage(validateNameAndSurname(data.name), "Wrong name"),
                    statusMessage(validateNameAndSurname(data.surname), "Wrong surname")
            )

    private val messages: List<String>
        get() = allValidation().filterNotNull()


    private val status: Boolean
         get() = messages.isEmpty()

    private fun validateUsername(username: String) =
            username.length > 3 && username.length < 20 && username.contains(USERNAME_PATTERN)

    private fun validatePassword(password: String) =
            password.length > 7 && password.length < 30 && password.contains(PASSWORD_PATTERN)

    private fun validateNameAndSurname(name: String) =
            name.length > 2 && name.length < 25 && name.contains(NAME_PATTERN)

    private val statusMessage: (Boolean, String) -> String? = { status, message ->
        when(status) {
            false -> message
            true -> null
        }
    }

    companion object{
        val PASSWORD_PATTERN = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#\$%]).{8,30}\$".toRegex()
        val USERNAME_PATTERN = "^[a-zA-Z0-9._-]{4,}\$".toRegex()
        val NAME_PATTERN = "[A-Za-zżźćńółęąśŻŹĆĄŚĘŁÓŃ]{1,25}['-]{0,2}[A-Za-zżźćńółęąśŻŹĆĄŚĘŁÓŃ]{1,25}['-]{0,2}[A-Za-zżźćńółęąśŻŹĆĄŚĘŁÓŃ]{1,25}".toRegex()
    }

}

