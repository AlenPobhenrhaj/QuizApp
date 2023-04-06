package com.example.quizapp.activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.quizapp.R
import com.example.quizapp.databinding.ActivityMainBinding
import com.example.quizapp.ui.QuizFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set up the ActionBar
        setSupportActionBar(binding.toolbar)

        // Set up the start quiz button
        binding.startQuizButton.setOnClickListener {
            val selectedCategory = binding.categorySpinner.selectedItem.toString()
            val selectedDifficulty = when (binding.difficultySlider.value.toInt()) {
                1 -> "easy"
                2 -> "medium"
                else -> "hard"
            }

            val bundle = Bundle().apply {
                putString("selectedCategory", getCategoryName(selectedCategory))
                putString("selectedDifficulty", selectedDifficulty)
                putInt("selectedCategoryID", selectedCategory)
            }

            // Start QuizFragment
            val quizFragment = QuizFragment()
            quizFragment.arguments = bundle
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, quizFragment)
                .addToBackStack(null)
                .commit()
        }
    }

    private fun getCategoryID(categoryName: String): Int {
        return when (categoryName) {
            "History" -> 1
            "General Knowledge" -> 2
            "Music" -> 3
            "Science" -> 4
            "Society & Culture" -> 5
            "Sport & Leisure" -> 6
            "Geography" -> 7
            // Add the other categories here
            else -> 0
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}


