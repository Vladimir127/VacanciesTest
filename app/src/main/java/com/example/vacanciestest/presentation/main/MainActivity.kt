package com.example.vacanciestest.presentation.main

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.example.vacanciestest.R
import com.example.vacanciestest.databinding.ActivityMainBinding
import com.example.vacanciestest.presentation.main.favorites.FavoritesSharedViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private lateinit var favoritesSharedViewModel: FavoritesSharedViewModel

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        initNavigation()

        val viewModelProvider = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)
        )
        favoritesSharedViewModel = viewModelProvider[FavoritesSharedViewModel::class.java]

        favoritesSharedViewModel.favoritesCount.observe(this) { count ->
            val badge = binding.bottomNavigationView.getOrCreateBadge(R.id.favoritesFragment)

            if (count == 0) {
                badge.isVisible = false
            } else {
                badge.isVisible = true
                badge.number = count
            }
        }

        favoritesSharedViewModel.loadFavoritesCount()
    }

    private fun initNavigation() {
        navController = Navigation.findNavController(this, R.id.nav_host_fragment)

        with(binding) {
            NavigationUI.setupWithNavController(bottomNavigationView, navController)
            bottomNavigationView.setOnApplyWindowInsetsListener(null)
        }
    }
}