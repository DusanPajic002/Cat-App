package com.example.mobilne2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.compositionLocalOf
import com.example.mobilne2.Acore.theme.Mobilne2Theme
import com.example.mobilne2.analytics.AppAnalytics
import com.example.mobilne2.navigation.ScreenManager
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    private val analytics = AppAnalytics()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val app = (applicationContext as App)

        setContent {
                Mobilne2Theme {

                    ScreenManager()
                }
        }
    }
}

val LocalAnalytics = compositionLocalOf<AppAnalytics> {
    error("Analytics not provided")
}