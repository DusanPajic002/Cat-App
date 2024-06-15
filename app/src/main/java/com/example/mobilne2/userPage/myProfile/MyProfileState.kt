package com.example.mobilne2.userPage.myProfile

import com.example.mobilne2.leaderBoardP.leaderBoard.model.LeaderBoardUI
import com.example.mobilne2.userPage.db.User

data class MyProfileState(
    val loading: Boolean = false,
    val publicLB : List<LeaderBoardUI> = emptyList(),
    val privateLB : List<LeaderBoardUI> = emptyList(),
    val leaderBoard : List<LeaderBoardUI> = emptyList(),
    val usersPerPage : List<LeaderBoardUI> = emptyList(),
    val page : Int = 1,
    val dataPerPage : Int = 8,
    val maxPage : Int = 1,
    val user: User? = null,
    val error: Error? = null,
){

    sealed class Events {
        data class changePage(val changePage: Int) : Events()
    }

    sealed class Error {
        data object LoadingFailed : Error()
    }

}
