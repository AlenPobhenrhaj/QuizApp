package com.example.quizapp.model

data class QuestionsItem(
    val category: String,
    val correctAnswer: String,
    val difficulty: String,
    val id: String,
    val incorrectAnswers: List<String>,
    val isNiche: Boolean,
    val question: String,
    val regions: List<Any>,
    val tags: List<String>,
    val type: String
)