package com.example.mobilne2.quizScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import coil.compose.AsyncImage
import coil.request.ImageRequest

fun NavGraphBuilder.quizScreen(
    route: String,
    onClose: () -> Unit,
) = composable(
    route = route,
) { navBackStackEntry ->

    val quizViewModel: QuizViewModel = hiltViewModel(navBackStackEntry)
    val state = quizViewModel.state.collectAsState()

    QuizScreen(
        data = state.value,
        onClose = onClose,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuizScreen(
    data: QuizState,
    onClose: () -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Cat Profile") }
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                Text(
                    text = "Cat Image",
                    fontSize = 24.sp,
                    modifier = Modifier.padding(16.dp)
                )
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(null) // imageUrl
                        .crossfade(true)
                        .build(),
                    contentDescription = "Loaded image",
                    modifier = Modifier
                        .fillMaxSize()
                )
                Button(
                    onClick = { /* Handle Button 1 Click */ },
                    modifier = Modifier.padding(8.dp)) {
                    Text(text = "Button 1")
                }
                Button(
                    onClick = { /* Handle Button 2 Click */ },
                    modifier = Modifier.padding(8.dp)) {
                    Text(text = "Button 2")
                }
                Button(
                    onClick = { /* Handle Button 3 Click */ },
                    modifier = Modifier.padding(8.dp)) {
                    Text(text = "Button 3")
                }
                Button(
                    onClick = { /* Handle Button 3 Click */ },
                    modifier = Modifier.padding(8.dp)) {
                    Text(text = "Button 3")
                }
            }
        }
    )
}