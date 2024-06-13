package com.example.mobilne2.leaderBoardP.leaderBoard

import com.example.mobilne2.leaderBoardP.leaderBoard.model.LeaderBoardUI


data class LeaderBState (
    val fetching: Boolean = false,
    val page : Int = 1,
    val maxPage : Int = 1,
    val dataPerPage : Int = 8,
    val leaderBoardOnline: List<LeaderBoardUI> = emptyList(),
    val leaderBoardOnlinePerPage: List<LeaderBoardUI> = emptyList(),
    val error: LeaderBError? = null,
) {
    sealed class Events {
        data class changePage(val changePage: Int) : Events()
    }
    sealed class LeaderBError {
        data class DataUpdateFailed(val cause: Throwable? = null) : LeaderBError()
    }
}