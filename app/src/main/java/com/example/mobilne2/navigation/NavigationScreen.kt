package com.example.mobilne2.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

fun NavGraphBuilder.navigationScreen(
    route: String,
    navController: NavController,
) = composable(
    route = route,
) { navBackStackEntry ->

    NavigationScreen(
        navController = navController,
    )
}

@Composable
fun NavigationScreen(
    navController: NavController,
) {
    Scaffold(
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                Text(
                    text = "Home",
                    fontSize = 36.sp,
                    modifier = Modifier
                        .padding(16.dp)
                        .padding(top = 64.dp)
                        .padding(bottom = 32.dp)
                )
                Button(
                    onClick = { navController.navigate(route = "quiz") },
                    modifier = Modifier
                        .padding(8.dp)
                        .size(width = 200.dp, height = 50.dp)
                ) {
                    Text(
                        text = "Quiz",
                        fontSize = 16.sp,
                    )
                }
                Button(
                    onClick = { navController.navigate(route = "cats") },
                    modifier = Modifier
                        .padding(8.dp)
                        .size(width = 200.dp, height = 50.dp)
                ) {
                    Text(
                        text = "Cats",
                        fontSize = 16.sp
                    )
                }
                Button(
                    onClick = { navController.navigate(route = "leaderBord") },
                    modifier = Modifier
                        .padding(8.dp)
                        .size(width = 200.dp, height = 50.dp)
                ) {
                    Text(
                        text = "LeaderBord",
                        fontSize = 16.sp,
                    )
                }
                Button(
                    onClick = { navController.navigate(route = "myProfile") },
                    modifier = Modifier
                        .padding(8.dp)
                        .size(width = 200.dp, height = 50.dp)
                ) {
                    Text(
                        text = "MyProfile",
                        fontSize = 16.sp,
                    )
                }
            }
        }
    )
}