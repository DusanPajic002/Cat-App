package com.example.mobilne2.quizScreen

import com.example.mobilne2.quizScreen.model.CatQuestion

data class QuizState (
    var questionNumber: Int = 0,
    var score: Int = 0,
    var questions: List<CatQuestion> = listOf()
     //
){

}