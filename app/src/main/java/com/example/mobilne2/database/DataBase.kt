package com.example.domaci2.database

import android.content.Context
import androidx.room.Room

object DataBase {

    lateinit var database: AppDatabase

    fun initDatabase(context: Context) {
        database = Room.databaseBuilder(
            context = context,
            klass = AppDatabase::class.java,
            name = "data.db"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

}