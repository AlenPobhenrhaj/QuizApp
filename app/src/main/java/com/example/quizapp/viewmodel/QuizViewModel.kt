package com.example.quizapp.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.quizapp.model.QuestionsItem
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

    private val _triviaQuestions = MutableLiveData<List<QuestionsItem>>()
    val triviaQuestions: MutableLiveData<List<QuestionsItem>> = _triviaQuestions

    private val viewModelScope = CoroutineScope(Dispatchers.IO)

    fun fetchTriviaQuestions(
        amount: Int,
        category: String? = null,
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
