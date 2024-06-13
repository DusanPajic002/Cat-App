package com.example.mobilne2.leaderBoardP.api

import com.example.mobilne2.leaderBoardP.api.model.LeaderBoardApiModel
import com.example.mobilne2.leaderBoardP.api.model.LeaderBoardReq
import com.example.mobilne2.leaderBoardP.db.LeaderBoard
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface LeaderBoardApi {

    @GET("leaderboard")
    suspend fun getLeaderboard(
        @Query("category") category: Int
    ): List<LeaderBoardApiModel>

    @POST("leaderboard")
    suspend fun publishLeaderBoard(
        @Body quizResult: LeaderBoardReq )


}