package com.example.mobilne2.leaderBoardP.api.model

import kotlinx.serialization.Serializable

@Serializable
data class LeaderBoardReq (
    val nickname: String,
    val result: Double,
    val category: Int
)