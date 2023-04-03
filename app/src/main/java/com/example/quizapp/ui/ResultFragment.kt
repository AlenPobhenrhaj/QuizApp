package com.example.quizapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.quizapp.R
import com.example.quizapp.databinding.FragmentResultBinding
import com.example.quizapp.viewmodel.QuizViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ResultFragment : Fragment() {

    private lateinit var binding: FragmentResultBinding
    private val viewModel: QuizViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.triviaQuestions.observe(viewLifecycleOwner, Observer { questions ->
            // Update the UI, such as showing the final score
        })

        binding.retryButton.setOnClickListener {
            // Handle user interactions, such as retrying the quiz
            findNavController().navigate(R.id.action_quizFragment_to_resultFragment)
        }
    }
}
