package com.example.mobilne2.quizScreen.quiz

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import coil.compose.rememberImagePainter
import com.example.mobilne2.R

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
            Image(
                painter = rememberImagePainter(data = R.drawable.image),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            Column(
                modifier = Modifier.fillMaxSize().padding(paddingValues),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                if(data.error != null) {
                   errorHandler( data = data, onClose = onClose )
                }else if (!data.loading && !data.finished && !data.cancled) {

                    quizTimer( data = data )

                    Spacer(modifier = Modifier.height(8.dp))

                    quizQuestion( data = data, eventPublisher = eventPublisher )

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = { eventPublisher(QuizState.Events.updateCancle(true)) },
                        modifier = Modifier.padding(8.dp).size(145.dp, 47.dp)
                    ) { Text(text = "Cancle", fontSize = 16.sp) }

                } else if (data.finished && !data.cancled && !data.loading) {
                    eventPublisher(QuizState.Events.updateScore)
                    QuizResult(score = data.score, eventPublisher, onClose = onClose)
                } else if (data.cancled && !data.finished && !data.loading){
                    QuizCancled(onClose = onClose)
                } else if (data.loading && !data.finished && !data.cancled){
                    QuizLoading()
                }

            }
        }
    )
}

@Composable
private fun quizQuestion(
    data: QuizState,
    eventPublisher: (QuizState.Events) -> Unit
){
    val questionInfo = data.questions[data.questionNumber]
    Text(
        text = "Q" + (data.questionNumber + 1) + ": " + questionInfo.question,
        fontSize = 24.sp,
        modifier = Modifier.padding(16.dp)
    )
    Image(
        painter = rememberImagePainter(questionInfo.catImage),
        contentDescription = "Cat Image",
        modifier = Modifier.height(200.dp).padding(16.dp).fillMaxWidth().clip(RoundedCornerShape(8.dp))
    )
    questionInfo.answers.forEachIndexed { index, answer ->
        Button(
            onClick = {
                eventPublisher(QuizState.Events.updateCorrectAnswers(answer))
                eventPublisher(QuizState.Events.changeQuestion((data.questionNumber + 1)))
                if(data.questionNumber == data.questions.size-1)
                    eventPublisher(QuizState.Events.updateFinish(true))
            },
            modifier = Modifier.padding(8.dp).size(195.dp, 47.dp)
        ) {
            Text(text = answer.capitalize(), fontSize = 19.sp)
        }
    }
}

@Composable
fun quizTimer(
    data: QuizState
){
    Box(
        modifier = Modifier.padding(20.dp).background(Color.Gray, shape = RoundedCornerShape(10.dp)).padding(10.dp)
    ) {
        Text(
            text = String.format("%02d:%02d", data.remainingTime / 60, data.remainingTime % 60),
            fontSize = 30.sp,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.align(Alignment.Center)
        )
    }
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
        modifier = Modifier.fillMaxSize().padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text( text = "Your score is: 0", fontSize = 24.sp, )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    onClose()
                },
                modifier = Modifier.padding(16.dp)
            ) { Text(text = "Home") }
        }
    }
}
@Composable
fun QuizResult(
    score: Double,
    eventPublisher: (QuizState.Events) -> Unit,
    onClose: () -> Unit
) {
    val publicPrivate = remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),

        ) {
            Column(
                modifier = Modifier
                    .padding(24.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Your score is: $score",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(24.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Checkbox(
                        checked = publicPrivate.value,
                        onCheckedChange = { publicPrivate.value = it }
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Publish online.",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                }
                Spacer(modifier = Modifier.height(24.dp))
                Button(
                    onClick = {
                        eventPublisher(QuizState.Events.publishEvent(publicPrivate.value))
                        onClose()
                    },
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Publish")
                }
            }
        }
    }
}

@Composable
private fun errorHandler(
    data: QuizState,
    onClose: () -> Unit
){
    when(data.error) {
        is QuizState.Error.ErrorToLoadQuiz -> {
            Text(text = "Error to load quiz.", fontSize = 24.sp)
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = { onClose() },
                modifier = Modifier.padding(8.dp).size(145.dp, 47.dp)
            ){ Text(text = "Go to Home") }
        }
        else -> { }
    }
}