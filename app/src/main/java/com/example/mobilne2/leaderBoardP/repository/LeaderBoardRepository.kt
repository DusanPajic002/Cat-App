package com.example.mobilne2.leaderBoardP.repository

import com.example.mobilne2.database.AppDatabase
import com.example.mobilne2.leaderBoardP.api.LeaderBoardApi
import javax.inject.Inject

class LeaderBoardRepository @Inject constructor(
    private val leaderBoardApi: LeaderBoardApi,
    private val database: AppDatabase,
) {


}