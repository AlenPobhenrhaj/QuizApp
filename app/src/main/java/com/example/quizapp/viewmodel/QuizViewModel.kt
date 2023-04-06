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
class QuizViewModel @Inject constructor(private val triviaRepository: TriviaRepository) : ViewModel() {

    private val _quizQuestions = MutableLiveData<Resource<List<QuestionsItem>>>()
    val quizQuestions: LiveData<Resource<List<QuestionsItem>>> get() = _quizQuestions

    fun fetchQuestions(categoryId: Int, difficulty: String)  {
        viewModelScope.launch {
            _quizQuestions.value = Resource.Loading()
            try {
                val response = triviaRepository.getTriviaQuestions(
                    amount = 10,
                    category = categoryId,
                    difficulty = difficulty.lowercase()
                )
                _quizQuestions.value = Resource.Success(response)
            } catch (e: Exception) {
                _quizQuestions.value = e.message?.let { Resource.Error(it) }
            }
        }

    }
}