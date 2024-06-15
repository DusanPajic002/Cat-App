package com.example.mobilne2.leaderBoardP.db
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface LeaderBoardDao {

    @Query("SELECT * FROM leaderboard WHERE nickname = :nickname")
    suspend fun getAllByNickname(nickname: String): List<LeaderBoard>

    @Query("SELECT * FROM leaderboard WHERE nickname = :nickname")
    suspend fun getPLBbyNickName(nickname: String): List<LeaderBoard>

    @Query("SELECT * FROM leaderboard WHERE userID = :userID")
    suspend fun getPLBbyUserID(userID: Int): List<LeaderBoard>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertToLeaderBoard(leaderBoard: LeaderBoard)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllLeaderBoard(leaderBoard: List<LeaderBoard>)

}