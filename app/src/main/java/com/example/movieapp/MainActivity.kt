package com.example.movieapp.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.ViewModelProvider
import com.example.movieapp.ui.screens.MovieListScreen
import com.example.movieapp.viewmodel.MovieViewModel
import com.example.movieapp.ui.theme.MovieAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize the MovieViewModel
        val movieViewModel = ViewModelProvider(this).get(MovieViewModel::class.java)

        setContent {
            MovieAppTheme {
                // Display the MovieListScreen
                MovieListScreen(movieViewModel = movieViewModel)
            }
        }
    }
}

