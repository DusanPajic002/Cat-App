package com.example.mobilne2.quizScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import coil.compose.AsyncImage
import coil.compose.rememberImagePainter
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
        eventPublisher = {
            quizViewModel.setEvent(it)
        },
        onClose = onClose,
    )
}

@Composable
fun QuizScreen(
    data: QuizState,
    eventPublisher: (QuizState.Events) -> Unit,
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
                if (!data.loading && data.questions.isNotEmpty() && !data.finished) {
                    val questionInfo = data.questions[data.questionNumber]
                    Text(
                        text = "Q" + (data.questionNumber + 1) + ": " + questionInfo.question,
                        fontSize = 24.sp,
                        modifier = Modifier.padding(16.dp)
                    )
                    Image(
                        painter = rememberImagePainter(questionInfo.catImage),
                        contentDescription = "Cat Image",
                        modifier = Modifier
                            .height(200.dp)
                            .padding(16.dp)
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                    )
                    questionInfo.answers.forEachIndexed { index, answer ->
                        Button(
                            onClick = {
                                eventPublisher(QuizState.Events.updateCorrectAnswers(answer))
                                eventPublisher(QuizState.Events.changeQuestion((data.questionNumber + 1)))
                            },
                            modifier = Modifier
                                .padding(8.dp)
                                .size(180.dp, 45.dp)
                        ) {
                            Text(text = answer, fontSize = 14.sp)

                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = { eventPublisher(QuizState.Events.updateFinish(true)) },
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(text = "Close")
                    }
                }
                else if (data.finished)
                    QuizResult(score = data.score, onClose = onClose)
                else
                    QuizLoading()

            }
        }
    )
}

@Composable
fun QuizLoading() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(text = "Generating quiz...")
            Spacer(modifier = Modifier.height(16.dp))
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        }

    }
}


@Composable
fun QuizResult(
    score: Int,
    onClose: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Your score is: $score",
                fontSize = 24.sp,
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    onClose()
                },
                modifier = Modifier.padding(16.dp)
            ) {
                Text(text = "Close")
            }
        }
    }
}