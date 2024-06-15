package com.example.mobilne2.leaderBoardP.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class LeaderBoard(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val category: Int = 1,
    val userID: Int = 0,
    val nickname: String = "",
    val result: Double = 0.0,
    val createdAt: Long = 0
)