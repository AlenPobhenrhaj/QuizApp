package com.example.quizapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.example.quizapp.databinding.FragmentQuizBinding
import com.example.quizapp.utils.Resource
import com.example.quizapp.viewmodel.QuizViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class QuizFragment : Fragment() {

    private var _binding: FragmentQuizBinding? = null
    private val binding get() = _binding!!
    private val viewModel: QuizViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentQuizBinding.inflate(inflater, container, false)

        val selectedCategory = arguments?.getInt("selectedCategory") ?: "Default Category"
        val selectedDifficulty = arguments?.getString("selectedDifficulty") ?: "1"

        // Fetch questions based on selectedCategory and selectedDifficulty
        viewModel.fetchQuestions(selectedCategory as Int, selectedDifficulty)

        // Observe quizQuestions LiveData and update UI accordingly
        viewModel.quizQuestions.observe(viewLifecycleOwner) { resource ->
            when (resource) {
                is Resource.Success -> {
                    // Display the first question and its answers
                    val question = resource.resourceData?.get(0)
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

        return binding.root

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

