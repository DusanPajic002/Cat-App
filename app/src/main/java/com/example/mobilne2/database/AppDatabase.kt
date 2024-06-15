package com.example.mobilne2.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.mobilne2.catListP.db.Cat
import com.example.mobilne2.catListP.db.CatListDao
import com.example.mobilne2.catProfile.db.CatImages
import com.example.mobilne2.catProfile.db.CatProfileDao
import com.example.mobilne2.leaderBoardP.db.LeaderBoard
import com.example.mobilne2.leaderBoardP.db.LeaderBoardDao
import com.example.mobilne2.userPage.db.User
import com.example.mobilne2.userPage.db.UserDao

@Database(
    entities = [
        Cat::class,
        CatImages::class,
        User::class,
        LeaderBoard::class,
    ],
    version = 6,
    exportSchema = true,
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun catListDao(): CatListDao
    abstract fun leaderBoardDao(): LeaderBoardDao
    abstract fun catProfileDao(): CatProfileDao
    abstract fun userDao(): UserDao
}