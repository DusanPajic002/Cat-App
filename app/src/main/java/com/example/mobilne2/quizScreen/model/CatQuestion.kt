package com.example.mobilne2.quizScreen.model

data class CatQuestion(
    val question: String = "",
    val catImage: String = "",
    val answers: List<String> = emptyList(),
    val correctAnswer: String = "",
)