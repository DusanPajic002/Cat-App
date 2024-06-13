package com.example.mobilne2.quizScreen.quiz

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import coil.compose.rememberImagePainter

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
                if (!data.loading && !data.finished && !data.cancled) {
                    val questionInfo = data.questions[data.questionNumber]
                    Text(
                        text = "${data.remainingTime / 60} : ${data.remainingTime % 60}",
                        fontSize = 30.sp,
                        modifier = Modifier.padding(top = 20.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
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
                                if(data.questionNumber == data.questions.size-1)
                                    eventPublisher(QuizState.Events.updateFinish(true))
                            },
                            modifier = Modifier
                                .padding(8.dp)
                                .size(195.dp, 47.dp)
                        ) {
                            Text(text = answer.capitalize(), fontSize = 19.sp)
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = { eventPublisher(QuizState.Events.updateCancle(true)) },
                        modifier = Modifier
                            .padding(8.dp)
                            .size(145.dp, 47.dp)
                    ) {
                        Text(text = "Cancle", fontSize = 16.sp)
                    }
                } else if (data.finished){
                    eventPublisher(QuizState.Events.updateScore)
                    QuizResult(score = data.score, eventPublisher, onClose = onClose)
                } else if (data.cancled){
                    QuizCancled(onClose = onClose)
                } else if (data.loading){
                    QuizLoading()
                }

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
            Text(text = "Generating quiz...", fontSize = 24.sp)
            Spacer(modifier = Modifier.height(16.dp))
            CircularProgressIndicator()
        }

    }
}

@Composable
fun QuizCancled(
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
                text = "Your score is: 0",
                fontSize = 24.sp,
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    onClose()
                },
                modifier = Modifier.padding(16.dp)
            ) {
                Text(text = "Home")
            }
        }
    }
}

@Composable
fun QuizResult(
    score: Double,
    eventPublisher: (QuizState.Events) -> Unit,
    onClose: () -> Unit
) {
    val public_private = remember { mutableStateOf(false) }

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
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Checkbox(
                    checked = public_private.value,
                    onCheckedChange = { public_private.value = it }
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Publish online.")
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    eventPublisher(QuizState.Events.publishEvent(public_private.value))
                    onClose()
                },
                modifier = Modifier.padding(16.dp)
            ) {
                Text(text = "Publish")
            }
        }
    }
}
