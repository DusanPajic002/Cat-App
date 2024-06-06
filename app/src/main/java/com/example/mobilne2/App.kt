package com.example.domaci2

import android.app.Application
import android.util.Log
import com.example.mobilne2.auth.AuthStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

class App : Application() {

    @Inject
    lateinit var authStore: AuthStore

    private val scope = CoroutineScope(Dispatchers.IO)

    override fun onCreate() {
        super.onCreate()


        val authData = authStore.authData.value
        Log.d("DATASTORE", "AuthData = $authData")

    }
}