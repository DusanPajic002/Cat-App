package com.example.mobilne2.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.mobilne2.catListP.db.Cat
import com.example.mobilne2.catListP.db.CatListDao
import com.example.mobilne2.catProfile.db.CatImages
import com.example.mobilne2.catProfile.db.CatProfileDao
import com.example.mobilne2.leaderBoardP.db.LeaderBoardDao

@Database(
    entities = [
        Cat::class,
        CatImages::class
    ],
    version = 2,
    exportSchema = true,
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun catListDao(): CatListDao
    abstract fun leaderBoardDao(): LeaderBoardDao
    abstract fun catProfileDao(): CatProfileDao

}