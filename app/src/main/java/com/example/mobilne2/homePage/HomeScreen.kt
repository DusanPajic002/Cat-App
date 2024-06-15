package com.example.mobilne2.homePage

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

fun NavGraphBuilder.homeScreen(
    route: String,
    navController: NavController,
) = composable(
    route = route,
) { navBackStackEntry ->

    val homeViewModel: HomeViewModel = hiltViewModel(navBackStackEntry)
    val state = homeViewModel.state.collectAsState()

    HomeScreen(
        navController = navController,
        data = state.value,
    )
}

@Composable
fun HomeScreen(
    navController: NavController,
    data: HomeState,
) {
    Scaffold(
        content = { paddingValues ->
            if(data.error != null && data.error is HomeState.Error.LoadingFailed){
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Top
                    ) {
                        Text(
                            text = data.error.message,
                            fontSize = 36.sp,
                            modifier = Modifier
                                .padding(16.dp)
                                .padding(top = 64.dp)
                                .padding(bottom = 32.dp)
                        )
                    }
            } else if(!data.fetching){
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
                            fontSize = 20.sp,
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
                            fontSize = 20.sp,
                        )
                    }
                    Button(
                        onClick = { navController.navigate(route = "leaderBoard") },
                        modifier = Modifier
                            .padding(8.dp)
                            .size(width = 200.dp, height = 50.dp)
                    ) {
                        Text(
                            text = "LeaderBoard",
                            fontSize = 20.sp,
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
                            fontSize = 20.sp,
                        )
                    }
                }
            }else{
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center,
                ) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
            }
        }
    )
}