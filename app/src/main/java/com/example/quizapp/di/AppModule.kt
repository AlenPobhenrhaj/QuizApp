package com.example.quizapp.di

import com.example.quizapp.api.QuizApi
import com.example.quizapp.api.TriviaApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideTriviaApiService(): TriviaApiService {
        return QuizApi.apiService
    }
}
