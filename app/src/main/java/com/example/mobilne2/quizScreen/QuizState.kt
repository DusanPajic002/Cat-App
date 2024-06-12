package com.example.mobilne2.quizScreen

import com.example.mobilne2.quizScreen.model.CatQuestion

data class QuizState (
    var questionNumber: Int = 0,
    var loading: Boolean = true,
    var score: Int = 0,
    var temp: List<String> = emptyList(),
    var breed: List<String> = emptyList(),
    var questions: List<CatQuestion> = emptyList()
     //
){

}