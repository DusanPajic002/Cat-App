package com.example.mobilne2.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.domaci2.catListP.db.Cat
import com.example.domaci2.catListP.db.CatListDao

@Database(
    entities = [
        Cat::class,
    ],
    version = 1,
    exportSchema = true,
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun catListDao(): CatListDao

}