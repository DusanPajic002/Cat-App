package com.example.mobilne2.quizScreen.quiz

import com.example.mobilne2.quizScreen.model.CatQuestion
import com.example.mobilne2.userPage.db.User

data class QuizState(
    val userNickname: String = "Unknown",
    val userID: Int = 0,
    val questionNumber: Int = 0,
    val loading: Boolean = true,
    val score: Double = 0.0,
    val remainingTime: Long = 0,
    val fullTime: Int = 0,
    val doTransition: Boolean = false,
    val finished: Boolean = false,
    val cancled: Boolean = false,
    val correctAnswers: Int = 0,
    val category: Int = 0,
    val temp: List<String> = emptyList(),
    val breed: List<String> = emptyList(),
    val questions: List<CatQuestion> = emptyList(),
    val error: Error? = null
){
    sealed class Events {
        data object updateScore : Events()
        data class changeQuestion(val number: Int, val doTransition: Boolean) : Events()
        data class updateCorrectAnswers(val answer: String) : Events()
        data class updateFinish(val finished: Boolean) : Events()
        data class publishEvent(val publish: Boolean) : Events()
        data class updateCancle(val cancled: Boolean) : Events()

    }

    sealed class Error {
        data object ErrorToLoadQuiz : Error()
    }


}