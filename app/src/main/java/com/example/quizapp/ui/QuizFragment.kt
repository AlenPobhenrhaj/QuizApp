package com.example.quizapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.quizapp.databinding.FragmentQuizBinding
import com.example.quizapp.model.Questions
import com.example.quizapp.viewmodel.QuizViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class QuizFragment : Fragment() {

    private lateinit var binding: FragmentQuizBinding
    private val viewModel: QuizViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentQuizBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.triviaQuestions.observe(viewLifecycleOwner, Observer { questions ->
            updateUI(questions)
        })

        binding.submitButton.setOnClickListener {
            // Handle user interactions, such as answering a question
        }

        viewModel.fetchTriviaQuestions(amount = 10) // Fetch trivia questions
    }

    private fun updateUI(questions: List<Questions>) {
        // Check if there are any questions
        if (questions.isEmpty()) return

        // Get the current question
        val currentQuestion = questions[0] // Change the index based on the current question number

        // Update the question text
        binding.questionText.text = currentQuestion.question

        // Update the answer options
        val options = currentQuestion.options
        if (options.size >= 4) {
            binding.optionA.text = options[0]
            binding.optionB.text = options[1]
            binding.optionC.text = options[2]
            binding.optionD.text = options[3]
        }
    }

}
