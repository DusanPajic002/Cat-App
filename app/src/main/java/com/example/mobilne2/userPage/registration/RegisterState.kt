package com.example.mobilne2.userPage.registration

data class RegisterState(
    val fatching: Boolean = false,
    val exist: Boolean = false,
    val error: Error? = null,
    val SuccesRegister: Boolean = false,
){

    sealed class Events {
        data class Register(val fullName: String, val nickname: String, val email: String ) : Events()
    }

    sealed class Error(val message: String) {
        class LoadingFailed : Error("Failed to load data.")
        class MissingParts : Error("All fields are mandatory")
        class BadFullName : Error("Please enter your name in the format 'Name Surname'")
        class BadNickname : Error("You can only use letters, numbers and underscore '_'")
        class BadEmail : Error("Please enter your email in the format email@email.com.")
        class PersonExist : Error("Person already exists, change your nickname")
    }
}
