package com.example.mobilne2.quizScreen.quiz

import com.example.mobilne2.quizScreen.model.CatQuestion

data class QuizState(
    val questionNumber: Int = 0,
    val loading: Boolean = true,
    val score: Double = 0.0,
    val remainingTime: Long = 0,
    val fullTime: Int = 0,
    val finished: Boolean = false,
    val cancled: Boolean = false,
    val correctAnswers: Int = 0,
    val temp: List<String> = emptyList(),
    val breed: List<String> = emptyList(),
    val questions: List<CatQuestion> = emptyList()
){
    sealed class Events {
        data object updateScore : Events()
        data class changeQuestion(val number: Int) : Events()
        data class updateCorrectAnswers(val answer: String) : Events()
        data class updateFinish(val finished: Boolean) : Events()
        data class publishEvent(val publish: Boolean) : Events()
        data class updateCancle(val cancled: Boolean) : Events()

    }


}