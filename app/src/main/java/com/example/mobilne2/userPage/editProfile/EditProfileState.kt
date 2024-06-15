package com.example.mobilne2.userPage.editProfile

import com.example.mobilne2.userPage.modelUI.UserUI

data class EditProfileState(
    val fatching: Boolean = false,
    val user : UserUI? = null,
    val firstName: String = "",
    val lastName: String = "",
    val nickname: String = "",
    val email: String = "",
    val error: Error? = null,
){

    sealed class Events {
        data class EditFirstName(val firstName: String) : Events()
        data class EditLastName(val lastName: String) : Events()
        data class EditNickname(val nickname: String) : Events()
        data class EditEmail(val email: String) : Events()
    }

    sealed class Error (val message: String){
        class PersonExist : Error("Person already exists, change your nickname.")
        class LoadingFailed : Error("Failed to load data.")
        class BadFirstName : Error("Please enter your firstname in the format 'Firstname'.")
        class BadLastName : Error("Please enter your lastname in the format 'Lastname'.")
        class BadNickname : Error("You can only use letters, numbers and underscore '_'.")
        class BadEmail : Error("Please enter your email in the format email@email.com.")
    }
}
