package com.example.mobilne2.leaderBoardP.mapper

import com.example.mobilne2.leaderBoardP.api.model.LeaderBoardApiModel
import com.example.mobilne2.leaderBoardP.db.LeaderBoard
import com.example.mobilne2.leaderBoardP.leaderBoard.model.LeaderBoardUI

fun LeaderBoard.asLeaderBoardUI(): LeaderBoardUI {
    return LeaderBoardUI(
        category = category,
        nickname = nickname,
        result = result,
        createdAt = createdAt
    )
}

fun LeaderBoardApiModel.asLeaderBoardUI(): LeaderBoardUI {
    return LeaderBoardUI(
        category = category,
        nickname = nickname,
        result = result,
        createdAt = createdAt
    )
}

