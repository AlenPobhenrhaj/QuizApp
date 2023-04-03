package com.example.quizapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.quizapp.model.Questions
import com.example.quizapp.repository.TriviaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class QuizViewModel @Inject constructor(
    private val triviaRepository: TriviaRepository
) : ViewModel() {

    private val _triviaQuestions = MutableLiveData<List<Questions>>()
    val triviaQuestions: LiveData<List<Questions>> = _triviaQuestions

    private val viewModelScope = CoroutineScope(Dispatchers.IO)

    fun fetchTriviaQuestions(
        amount: Int,
        category: Int? = null,
        difficulty: String? = null,
        type: String? = null
    ) {
        viewModelScope.launch {
            try {
                val questions = triviaRepository.getTriviaQuestions(amount, category, difficulty, type)
                _triviaQuestions.postValue(questions)
            } catch (e: Exception) {
                // Handle the exception (e.g., show an error message or retry the API call)
            }
        }
    }
}
