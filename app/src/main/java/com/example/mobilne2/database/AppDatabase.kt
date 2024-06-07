package com.example.mobilne2.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.mobilne2.catListP.db.Cat
import com.example.mobilne2.catListP.db.CatListDao
import com.example.mobilne2.catProfile.db.CatImages
import com.example.mobilne2.catProfile.db.CatProfileDao

@Database(
    entities = [
        Cat::class,
       // CatImages::class
    ],
    version = 1,
    exportSchema = true,
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun catListDao(): CatListDao
   // abstract fun catProfileDao(): CatProfileDao

}