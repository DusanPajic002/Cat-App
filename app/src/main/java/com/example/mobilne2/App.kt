package com.example.domaci2

import android.app.Application
import com.example.domaci2.database.DataBase

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        DataBase.initDatabase(context = this)
    }
}
