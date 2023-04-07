package com.example.quizapp.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.quizapp.R
import com.example.quizapp.databinding.ActivityMainBinding
import com.example.quizapp.ui.QuizFragment
import dagger.hilt.android.AndroidEntryPoint
import android.widget.ArrayAdapter


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set up the spinner for selecting a category
        ArrayAdapter.createFromResource(
            this,
            R.array.categories,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.categorySpinner.adapter = adapter
        }

        // Set up the slider for selecting difficulty
        binding.difficultySlider.addOnChangeListener { _, value, _ ->
            binding.difficultyText.text = when (value.toInt()) {
                1 -> "Easy"
                2 -> "Medium"
                3 -> "Hard"
                else -> "Any"
            }
        }

        binding.startQuizButton.setOnClickListener {
            val selectedCategoryName = binding.categorySpinner.selectedItem.toString()
            val selectedDifficulty = when (binding.difficultySlider.value.toInt()) {
                1 -> "easy"
                2 -> "medium"
                3 -> "hard"
                else -> ""
            }

            val selectedCategoryIDs = getCategoryIDs(selectedCategoryName)

            val bundle = Bundle().apply {
                putString("selectedCategory", selectedCategoryName)
                putString("selectedDifficulty", selectedDifficulty)
                putIntegerArrayList("selectedCategoryIDs", ArrayList(selectedCategoryIDs))
            }

            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, QuizFragment::class.java, bundle)
                .commit()
        }
    }

    private fun getCategoryIDs(categoryName: String): List<Int> {
        return when (categoryName) {
            "General Knowledge" -> listOf(9)
            "History" -> listOf(23)
            "Geography" -> listOf(22)
            "Film & Tv" -> listOf(11)
            "Music" -> listOf(12)
            "Sport & Leisure" -> listOf(21)
            "Society & Culture" -> listOf(13)
            else -> emptyList()
        }
    }
}

