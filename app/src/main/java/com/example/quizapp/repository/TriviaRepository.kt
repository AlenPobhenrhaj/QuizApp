package com.example.quizapp.repository

import com.example.quizapp.api.TriviaApiService
import com.example.quizapp.model.QuestionsItem
import javax.inject.Inject

class TriviaRepository @Inject constructor(
    private val apiService: TriviaApiService
) {
    suspend fun getTriviaQuestions(
        categoryIDs: List<Int>,
        difficulty: String? = null,
        type: String? = null
    ): List<QuestionsItem> {
        val questions = mutableListOf<QuestionsItem>()
        for (categoryID in categoryIDs) {
            val response = apiService.getTriviaQuestions(1, categoryID, difficulty, type)
            if (response.isSuccessful) {
                response.body()?.let { questions.addAll(it) }
            } else {
                throw Exception("Error fetching trivia questions: ${response.message()}")
            }
        }
        return questions
    }
}
