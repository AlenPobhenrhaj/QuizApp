package com.example.quizapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.quizapp.databinding.FragmentSelectionBinding

class SelectionFragment : Fragment() {

    private lateinit var binding: FragmentSelectionBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSelectionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.startQuizButton.setOnClickListener {
            val selectedCategory = binding.categorySpinner.selectedItem.toString()
            val selectedDifficulty = binding.difficultySlider.value.toInt().toString()

            // Pass the selected category and difficulty to QuizFragment
            val bundle = Bundle().apply {
                putString("selectedCategory", selectedCategory)
                putString("selectedDifficulty", selectedDifficulty)
            }

            findNavController().navigate(R.id.action_selectionFragment_to_quizFragment, bundle)
        }
    }
}
