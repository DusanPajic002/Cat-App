package com.example.mobilne2.leaderBoardP.api


data class LeaderBoardApiModel(
    val category: Int,
    val nickname: String,
    val result: Double,
    val createdAt: String
)
