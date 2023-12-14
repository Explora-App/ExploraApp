package com.example.explora.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.explora.R
import com.example.explora.ui.history.HistoryFragment
import com.example.explora.ui.home.HomeFragment
import com.example.explora.ui.quiz.QuizFragment
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {

    private lateinit var bottomNavigation: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        bottomNavigation = findViewById(R.id.bottomNavigation)
        bottomNavigation.setOnItemSelectedListener  { item ->
            when (item.itemId) {
                R.id.action_quiz -> replaceFragment(QuizFragment())
                R.id.action_home -> replaceFragment(HomeFragment())
                R.id.action_history -> replaceFragment(HistoryFragment())
            }
            true
        }

        if (savedInstanceState == null) {
            bottomNavigation.selectedItemId = R.id.action_home
        }

    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}