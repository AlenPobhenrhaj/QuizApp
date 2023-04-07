package com.example.quizapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quizapp.model.QuestionsItem
import com.example.quizapp.repository.TriviaRepository
import com.example.quizapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuizViewModel @Inject constructor(
    private val repository: TriviaRepository
) : ViewModel() {

    private val _quizQuestions = MutableLiveData<Resource<List<QuestionsItem>>>()
    val quizQuestions: LiveData<Resource<List<QuestionsItem>>> = _quizQuestions

    fun fetchQuestions(selectedCategoryIDs: List<Int>, selectedDifficulty: String) {
        _quizQuestions.value = Resource.Loading()
        viewModelScope.launch {
            try {
                val questions = repository.getTriviaQuestions(selectedCategoryIDs, selectedDifficulty)
                _quizQuestions.value = Resource.Success(questions)
            } catch (e: Exception) {
                _quizQuestions.value = Resource.Error(e.message ?: "An unknown error occurred")
            }
        }
    }
}
