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
    suspend fun getUserByID(id : Int): User {
        return database.userDao().getUserByID(id)
    }
    suspend fun updateFirstName(userID: Int, firstName: String) {
        database.userDao().updateFirstName(userID, firstName)
    }
    suspend fun updateLastName(userID: Int, lastName: String) {
        database.userDao().updateLastName(userID, lastName)
    }
    suspend fun updateEmail(userID: Int, email: String) {
        database.userDao().updateEmail(userID, email)
    }
    suspend fun updateNickname(userID: Int, nickname: String) {
        database.userDao().updateNickname(userID, nickname)
    }

}