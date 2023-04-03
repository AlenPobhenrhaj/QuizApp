package com.example.quizapp.api

import com.example.quizapp.model.Questions
import com.example.quizapp.model.QuestionsItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface TriviaApiService {
    @GET("api/questions")
    suspend fun getTriviaQuestions(
        @Query("amount") amount: Int,
        @Query("category") category: Int? = null,
        @Query("difficulty") difficulty: String? = null,
        @Query("type") type: String? = null
    ): Response<List<Questions>>
}