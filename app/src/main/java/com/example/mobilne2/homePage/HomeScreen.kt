package com.example.mobilne2.homePage

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ListItemDefaults.contentColor
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import coil.compose.rememberImagePainter
import com.example.mobilne2.R

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

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    navController: NavController,
    data: HomeState,
) {
    Scaffold(
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Image(
                    painter = rememberImagePainter(data = R.drawable.image),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
                if (data.error != null && data.error is HomeState.Error.LoadingFailed) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize(),
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
                } else if (!data.fetching) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize(),
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

                        navigationButtons(
                            clickNavigation = { navController.navigate(route = "quiz") },
                            name = "Quiz"
                        )

                        navigationButtons(
                            clickNavigation = { navController.navigate(route = "cats") },
                            name = "Cats"
                        )

                        navigationButtons(
                            clickNavigation = { navController.navigate(route = "leaderBoard") },
                            name = "LeaderBoard"
                        )

                        navigationButtons(
                            clickNavigation = { navController.navigate(route = "myProfile") },
                            name = "MyProfile"
                        )
                    }
                } else {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center,
                    ) {
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                    }
                }
            }
        }
    )
}


@Composable
fun navigationButtons(
    clickNavigation: () -> Unit,
    name : String
){
    Button(
        onClick = { clickNavigation() },
        //colors = ButtonDefaults.buttonColors(containerColor = Color.Gray),
        modifier = Modifier
            .padding(8.dp)
            .size(width = 200.dp, height = 50.dp)
    ) {
        Text(
            text = name,
            fontSize = 20.sp,
        )
    }
}