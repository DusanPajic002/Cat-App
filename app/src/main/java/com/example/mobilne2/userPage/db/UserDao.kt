package com.example.mobilne2.userPage.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertUser(user: User)

    @Query("SELECT COUNT(*) FROM users")
    suspend fun getUserCount(): Int

    @Query("SELECT * FROM users WHERE id = :userID")
    suspend fun getUserByID(userID: Int): User

    @Query("UPDATE users SET firstName = :firstName WHERE id = :userID")
    suspend fun updateFirstName(userID: Int, firstName: String)

    @Query("UPDATE users SET lastName = :lastName WHERE id = :userID")
    suspend fun updateLastName(userID: Int, lastName: String)

    @Query("UPDATE users SET email = :email WHERE id = :userID")
    suspend fun updateEmail(userID: Int, email: String)

    @Query("UPDATE users SET nickname = :nickname WHERE id = :userID")
    suspend fun updateNickname(userID: Int, nickname: String)



}