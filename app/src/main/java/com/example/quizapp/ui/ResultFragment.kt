package com.example.quizapp.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.quizapp.databinding.FragmentResultBinding
import com.example.quizapp.R

class ResultFragment : Fragment() {

    private lateinit var binding: FragmentResultBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("StringFormatInvalid")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val correctAnswers = arguments?.getInt("correctAnswers") ?: 0
        val totalQuestions = arguments?.getInt("totalQuestions") ?: 0
        val resultText = getString(R.string.you_scored_0_out_of_10, correctAnswers, totalQuestions)

        binding.resultText.text = resultText

        binding.retryButton.setOnClickListener {
            findNavController().navigate(R.id.action_resultFragment_to_quizFragment)
        }
    }
}
