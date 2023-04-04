package com.example.quizapp.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.quizapp.R
import com.example.quizapp.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize the spinner with categories
        val categories = arrayOf("Category 1", "Category 2", "Category 3")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categories)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.categorySpinner.adapter = adapter

        // Handle start quiz button click
        binding.startQuizButton.setOnClickListener {
            val selectedCategory = binding.categorySpinner.selectedItem.toString()
            val selectedDifficulty = binding.difficultySlider.progress.toString()

            // Pass the selected category and difficulty to QuizFragment
            val bundle = Bundle().apply {
                putString("selectedCategory", selectedCategory)
                putString("selectedDifficulty", selectedDifficulty)
            }

            findNavController(R.id.nav_host_fragment_activity_main).navigate(R.id.action_mainActivity_to_quizFragment, bundle)
        }

    }
}
