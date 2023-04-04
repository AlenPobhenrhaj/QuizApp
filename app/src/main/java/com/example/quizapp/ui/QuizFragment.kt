package com.example.quizapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.quizapp.databinding.FragmentQuizBinding
import com.example.quizapp.model.QuestionsItem
import com.example.quizapp.viewmodel.QuizViewModel
import dagger.hilt.android.AndroidEntryPoint
import androidx.navigation.fragment.findNavController
import com.example.quizapp.R

@AndroidEntryPoint
class QuizFragment : Fragment() {

    private lateinit var binding: FragmentQuizBinding
    private var correctAnswersCount = 0
    private val viewModel: QuizViewModel by viewModels()
    private var currentQuestionIndex = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentQuizBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val selectedCategory = arguments?.getString("selectedCategory")
        val selectedDifficulty = arguments?.getString("selectedDifficulty")

        viewModel.fetchTriviaQuestions(amount = 10, category = selectedCategory, difficulty = selectedDifficulty)

        viewModel.triviaQuestions.observe(viewLifecycleOwner, Observer { questions ->
            updateUI(questions, currentQuestionIndex)
        })

        binding.submitButton.setOnClickListener {
            val selectedOptionId = binding.answerOptions.checkedRadioButtonId
            val selectedOptionText = view.findViewById<RadioButton>(selectedOptionId)?.text.toString()

            val currentQuestion = viewModel.triviaQuestions.value?.get(currentQuestionIndex)
            if (currentQuestion?.correctAnswer == selectedOptionText) {
                Toast.makeText(context, "Correct!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Incorrect!", Toast.LENGTH_SHORT).show()
            }

            currentQuestionIndex++

            if (currentQuestion?.correctAnswer == selectedOptionText) {
                correctAnswersCount++
            }

            if (currentQuestionIndex < (viewModel.triviaQuestions.value?.size ?: 0)) {
                updateUI(viewModel.triviaQuestions.value!!, currentQuestionIndex)
            } else {
                val bundle = Bundle().apply {
                    putInt("correctAnswers", correctAnswersCount)
                    putInt("totalQuestions", viewModel.triviaQuestions.value?.size ?: 0)
                }
                findNavController().navigate(R.id.action_quizFragment_to_resultFragment, bundle)
            }
        }
    }

    private fun updateUI(questions: List<QuestionsItem>, questionIndex: Int) {
        if (questions.isEmpty() || questionIndex >= questions.size) return

        val currentQuestion = questions[questionIndex]

        binding.questionText.text = currentQuestion.question

        val options = currentQuestion.incorrectAnswers.toMutableList()
        options.add(currentQuestion.correctAnswer)
        options.shuffle()

        if (options.size >= 4) {
            binding.optionA.text = options[0]
            binding.optionB.text = options[1]
            binding.optionC.text = options[2]
            binding.optionD.text = options[3]
        }

        binding.answerOptions.clearCheck()
    }
}
