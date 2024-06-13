package com.example.mobilne2.leaderBoardP.api.model

data class LeaderBoardApiModel(
    var category: Int = 0,
    var nickname: String = "",
    var result: Double = 0.0,
    var createdAt: String = ""
)
