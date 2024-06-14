package com.example.mobilne2.userPage.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "users")
data class User(
    @PrimaryKey (autoGenerate = true) val id: Int,
    val firstName: String,
    val lastName: String,
    val nickname: String,
    val email: String,
)
