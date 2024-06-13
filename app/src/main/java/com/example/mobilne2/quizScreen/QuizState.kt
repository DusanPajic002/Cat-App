package com.example.mobilne2.quizScreen

import android.content.IntentSender.OnFinished
import com.example.mobilne2.quizScreen.model.CatQuestion

data class QuizState (
    val questionNumber: Int = 0,
    val loading: Boolean = true,
    val score: Int = 0,
    val finished: Boolean = false,
    val correctAnswers: Int = 0,
    val temp: List<String> = emptyList(),
    val breed: List<String> = emptyList(),
    val questions: List<CatQuestion> = emptyList()
     //
){

    sealed class Events {
        data class updateScore(val score: String) : Events()
        data class changeQuestion(val number: Int) : Events()
        data class updateCorrectAnswers(val answer: String) : Events()
        data class updateFinish(val finished: Boolean) : Events()

    }


}