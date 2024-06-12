package com.example.mobilne2.leaderBoardP.api.model

data class LeaderBApiModel(
    val rank: Int,
    val nickname: String,
    val score: Int,
    val totalQuizzes: Int
)
