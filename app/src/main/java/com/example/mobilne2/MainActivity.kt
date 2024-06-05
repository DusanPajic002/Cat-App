package com.example.domaci2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.domaci2.navigation.ScreenManager
import com.example.domaci2.Acore.theme.Domaci2Theme

class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            Domaci2Theme {

                ScreenManager()
            }
        }
    }
}