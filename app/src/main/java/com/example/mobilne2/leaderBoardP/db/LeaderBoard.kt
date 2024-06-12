package com.example.mobilne2.leaderBoardP.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class LeaderBoard  (
    @PrimaryKey var id: Int = 0,
    var category: Int = 0,
    var nickname: String = "",
    var result: Double = 0.0,
    var createdAt: String = ""
)