package com.example.quizapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.example.quizapp.databinding.FragmentQuizBinding
import com.example.quizapp.model.QuestionsItem
import com.example.quizapp.utils.Resource
import com.example.quizapp.viewmodel.QuizViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class QuizFragment : Fragment() {

    private var _binding: FragmentQuizBinding? = null
    private val binding get() = _binding!!
    private val viewModel: QuizViewModel by viewModels()
    private var currentQuestionIndex = 0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentQuizBinding.inflate(inflater, container, false)

        val selectedCategory = arguments?.getString("selectedCategory") ?: ""
        val selectedDifficulty = arguments?.getString("selectedDifficulty") ?: ""
        val selectedCategoryID = arguments?.getInt("selectedCategoryID") ?: 0

        // Fetch questions based on selectedCategory and selectedDifficulty
        viewModel.fetchQuestions(selectedCategoryID, selectedDifficulty)

        // Observe quizQuestions LiveData and update UI accordingly
        viewModel.quizQuestions.observe(viewLifecycleOwner) { resource ->
            when (resource) {

                is Resource.Success -> {
                    // Display the first question and its answers
                    val question = resource.resourceData?.get(0)
                    question?.let {
                        setupQuestion(it)
                    }

                    binding.questionText.text = question?.question
                    // Update the UI with the question's answers
                }
                is Resource.Error -> {
                    Toast.makeText(
                        requireContext(),
                        "Error: ${resource.resourceMessage}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                is Resource.Loading -> {
                    // Show a loading indicator if needed
                }
            }
        }

        // Add click listeners for answer buttons
        binding.optionA.setOnClickListener {
            submitAnswer(binding.optionA.text.toString())
        }

        binding.optionB.setOnClickListener {
            submitAnswer(binding.optionB.text.toString())
        }

        binding.optionC.setOnClickListener {
            submitAnswer(binding.optionC.text.toString())
        }

        binding.optionD.setOnClickListener {
            submitAnswer(binding.optionD.text.toString())
        }

        // Add click listener for the submit button
        binding.submitButton.setOnClickListener {
            // Handle submit button click
        }


        return binding.root

    }

    private fun setupQuestion(question: QuestionsItem) {
        binding.questionText.text = question.question
        val answers = question.incorrectAnswers.toMutableList()
        answers.add(question.correctAnswer)
        answers.shuffle()

        binding.optionA.text = answers[0]
        binding.optionB.text = answers[1]
        binding.optionC.text = answers[2]
        binding.optionD.text = answers[3]
    }

    private fun submitAnswer(selectedAnswer: String) {
        val question = viewModel.quizQuestions.value?.resourceData?.get(currentQuestionIndex)

        if (question?.correctAnswer == selectedAnswer) {
            Toast.makeText(requireContext(), "Correct!", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(requireContext(), "Incorrect!", Toast.LENGTH_SHORT).show()
        }

        currentQuestionIndex++

        if (currentQuestionIndex < (viewModel.quizQuestions.value?.resourceData?.size ?: 0)) {
            setupQuestion(viewModel.quizQuestions.value?.resourceData?.get(currentQuestionIndex)!!)
        } else {
            Toast.makeText(requireContext(), "Quiz Completed!", Toast.LENGTH_SHORT).show()
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
