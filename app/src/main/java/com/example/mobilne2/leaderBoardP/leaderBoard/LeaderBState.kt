package com.example.mobilne2.leaderBoardP.leaderBoard

import com.example.mobilne2.leaderBoardP.leaderBoard.model.LeaderBoardUI


data class LeaderBState (
    val fetching: Boolean = false,
    val leaderBoardPrivate: List<LeaderBoardUI> = emptyList(),
    val leaderBoardOnline: List<LeaderBoardUI> = emptyList(),
    val error: LeaderBError? = null,
) {
    sealed class LeaderBError {
        data class DataUpdateFailed(val cause: Throwable? = null) : LeaderBError()
    }
}