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
            val selectedCategory = binding.categorySpinner.selectedItem.toString()
            val selectedDifficulty = when (binding.difficultySlider.value.toInt()) {
                1 -> "easy"
                2 -> "medium"
                3 -> "hard"
                else -> ""
            }

            val bundle = Bundle().apply {
                putString("selectedCategory", selectedCategory)
                putString("selectedDifficulty", selectedDifficulty)
                putIntegerArrayList("selectedCategoryIDs", ArrayList(getCategoryID(selectedCategory)))
            }

            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, QuizFragment::class.java, bundle)
                .commit()
        }
    }

    private fun getCategoryID(categoryName: String): List<Int> {
        val categories = resources.getStringArray(R.array.categories)
        val categoryIDs = resources.getStringArray(R.array.category_ids)

        val index = categories.indexOf(categoryName)
        return if (index >= 0) {
            categoryIDs[index].split(",").map { it.toInt() }
        } else {
            listOf(0)
        }
    }
}

