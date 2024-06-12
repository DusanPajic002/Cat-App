package com.example.mobilne2.leaderBoardP.api

import com.example.mobilne2.leaderBoardP.api.model.LeaderBoardReq
import retrofit2.http.Body
import retrofit2.http.POST

interface LeaderBoardApi {

    //{ "nickname": "rma", "result": 88.88, "category": 1 }
    @POST("leaderboard")
    suspend fun addToLeaderBoard(@Body leaderBoardReq: LeaderBoardReq)

    // { "category": 2, "nickname": "111test", "result": 92.0, "createdAt": 1717621330048 }




}