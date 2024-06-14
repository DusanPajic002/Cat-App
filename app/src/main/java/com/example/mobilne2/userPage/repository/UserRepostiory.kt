package com.example.mobilne2.userPage.repository


import com.example.mobilne2.database.AppDatabase
import com.example.mobilne2.userPage.db.User
import javax.inject.Inject

class UserRepostiory @Inject constructor(
    private val database: AppDatabase,
) {

    suspend fun getUserCount(): Int {
        return database.userDao().getUserCount()
    }

    suspend fun insertUser(user: User) {
        database.userDao().insertUser(user)
    }

}