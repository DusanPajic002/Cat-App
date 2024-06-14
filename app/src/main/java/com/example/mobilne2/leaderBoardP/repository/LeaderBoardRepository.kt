package com.example.mobilne2.leaderBoardP.repository

import com.example.mobilne2.catListP.db.Cat
import com.example.mobilne2.catProfile.db.CatImages
import com.example.mobilne2.catProfile.mapper.asCatImageModel
import com.example.mobilne2.database.AppDatabase
import com.example.mobilne2.leaderBoardP.api.LeaderBoardApi
import com.example.mobilne2.leaderBoardP.api.model.LeaderBoardApiModel
import com.example.mobilne2.leaderBoardP.db.LeaderBoard
import com.example.mobilne2.userPage.db.User
import javax.inject.Inject

class LeaderBoardRepository @Inject constructor(
    private val leaderBoardApi: LeaderBoardApi,
    private val database: AppDatabase,
) {

    suspend fun getPLBbyNickName(nickname: String): List<LeaderBoard> {
        return database.leaderBoardDao().getPLBbyNickName(nickname)
    }
    suspend fun getLeaderBoardOnline(category: Int): List<LeaderBoardApiModel> {
        return leaderBoardApi.getLeaderboard(category)
    }

}