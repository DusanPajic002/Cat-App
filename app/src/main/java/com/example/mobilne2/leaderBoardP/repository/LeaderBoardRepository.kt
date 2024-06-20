package com.example.mobilne2.leaderBoardP.repository

import com.example.mobilne2.database.AppDatabase
import com.example.mobilne2.leaderBoardP.api.LeaderBoardApi
import com.example.mobilne2.leaderBoardP.api.model.LeaderBoardApiModel
import com.example.mobilne2.leaderBoardP.db.LeaderBoard
import javax.inject.Inject

class LeaderBoardRepository @Inject constructor(
    private val leaderBoardApi: LeaderBoardApi,
    private val database: AppDatabase,
) {

    suspend fun getPLBbyNickName(nickname: String): List<LeaderBoard> {
        return database.leaderBoardDao().getPLBbyNickName(nickname)
    }
    suspend fun getPLBbyUserID(userID: Int): List<LeaderBoard> {
        return database.leaderBoardDao().getPLBbyUserID(userID)
    }
    suspend fun getLeaderBoardOnline(category: Int): List<LeaderBoardApiModel> {
        return leaderBoardApi.getLeaderboard(category)
    }

}