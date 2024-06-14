package com.example.mobilne2.userPage.mapper


import com.example.mobilne2.userPage.db.User
import com.example.mobilne2.userPage.registration.model.UserUI

fun User.asUserUI(): UserUI {
    return UserUI(
        id = id,
        firstName = firstName,
        lastName = lastName,
        nickname = nickname,
        email = email,
    )
}