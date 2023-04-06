package com.example.quizapp.repository

import com.example.quizapp.api.TriviaApiService
import com.example.quizapp.model.QuestionsItem
import javax.inject.Inject

class TriviaRepository @Inject constructor (
    private val apiService: TriviaApiService
    )

{
    suspend fun getTriviaQuestions(
        amount: Int,
        category: Int? = null,
        difficulty: String? = null,
        type: String? = null
    ): List<QuestionsItem>
    {
        val response = apiService.getTriviaQuestions(amount, category, difficulty, type)
        if (response.isSuccessful) {
            return response.body() ?: emptyList()
        } else {
            throw Exception("Error fetching trivia questions: ${response.message()}")
        }

    }
}