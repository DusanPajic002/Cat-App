package com.example.mobilne2.userPage.editProfile

import com.example.mobilne2.userPage.modelUI.UserUI

data class EditProfileState(
    val fatching: Boolean = false,
    val user : UserUI? = null,
    val error: Error? = null,
){

    sealed class Events {
        data class EditFirstName(val firstName: String) : Events()
        data class EditLastName(val lastName: String) : Events()
        data class EditNickname(val nickname: String) : Events()
        data class EditEmail(val email: String) : Events()
    }

    sealed class Error {
        data object LoadingFailed : Error()
        data object BadFirstName: Error()
        data object BadLastName : Error()
        data object BadNickname : Error()
        data object BadEmail : Error()
        data object PersonExist : Error() // stavi tryblock i catch block hvata ako baza vrati abort
    }
}
