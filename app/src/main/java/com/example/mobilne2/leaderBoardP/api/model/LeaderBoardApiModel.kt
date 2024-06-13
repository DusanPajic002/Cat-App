package com.example.mobilne2.leaderBoardP.api.model

import kotlinx.serialization.Serializable

@Serializable
data class LeaderBoardApiModel(
    var category: Int = 0,
    var nickname: String = "",
    var result: Double = 0.0,
    var createdAt: Long = 0
)
