package com.example.mobilne2.quizScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.rememberPagerState
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

@Composable
fun QuizScreen(
    data: QuizState,
    onClose: () -> Unit,
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
                if (!data.loading && data.questions.isNotEmpty() && data.questionNumber < 20) {
                    Text(
                        text = data.questions.get(data.questionNumber).question,
                        fontSize = 24.sp,
                        modifier = Modifier.padding(16.dp)
                    )
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(data.questions.get(data.questionNumber).catImage) // imageUrl
                            .crossfade(true)
                            .build(),
                        contentDescription = "Loaded image",
                        modifier = Modifier
                            .fillMaxSize()
                    )
                    data.questions.get(data.questionNumber).answers.forEachIndexed { index, answer ->
                        Button(
                            onClick = {
                                data.questionNumber += 1
                            },
                            modifier = Modifier
                                .padding(8.dp)
                                .size(200.dp, 50.dp)
                        ) {
                            Text(text = answer)
                        }
                    }
                } else if(!data.loading && data.questions.isNotEmpty() && data.questionNumber > 19){
                    Text(
                        text = "Your score is: ${data.score}",
                        fontSize = 24.sp,
                        modifier = Modifier.padding(16.dp)
                    )
                } else{
                        Text(
                            text = "No questions available.",
                            fontSize = 24.sp,
                            modifier = Modifier.padding(16.dp)
                        )
                }
            }
        }
    )
}