package com.example.mobilne2.userPage.registration

data class UserState(
    val fatching: Boolean = false,
    val exist: Boolean = false,
    val error: Error? = null,
    val SuccesRegister: Boolean = false,
){

    sealed class Events {
        data class Register(val fullName: String, val nickname: String, val email: String ) : Events()
    }

    sealed class Error {
        data object MissingParts : Error()
        data object BadFullName: Error()
        data object BadNickname : Error()
        data object BadEmail : Error()
        data object PersonExist : Error()
    }
}
