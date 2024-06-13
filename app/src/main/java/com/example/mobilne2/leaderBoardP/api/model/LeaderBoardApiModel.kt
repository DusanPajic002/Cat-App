package com.example.mobilne2.leaderBoardP.api.model

import kotlinx.serialization.Serializable

@Serializable
data class LeaderBoardApiModel(
    val category: Int = 0,
    val nickname: String = "",
    val result: Double = 0.0,
    val createdAt: Long = 0
)
