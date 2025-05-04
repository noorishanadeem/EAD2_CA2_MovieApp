package com.example.movieapp.ui.screens

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.platform.LocalContext
import com.example.movieapp.viewmodel.MovieViewModel
import com.example.movieapp.model.Review

@Composable
fun MovieListScreen(movieViewModel: MovieViewModel) {
    LaunchedEffect(Unit) {
        movieViewModel.fetchMovies()
        println("DEBUG: Called fetchMovies()")
    }

    val moviesState = movieViewModel.movies.value
    val errorState = movieViewModel.error.value
    val isReviewSubmitted = movieViewModel.isReviewSubmitted.value

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Movies", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))

        if (errorState != null) {
            Text("Error: $errorState", color = MaterialTheme.colorScheme.error)
        } else if (moviesState.isEmpty()) {
            Text("No movies available.")
        } else {
            LazyColumn {
                items(moviesState) { movie ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(text = movie.title, style = MaterialTheme.typography.titleLarge)
                            Text(text = "Genre: ${movie.genre}", style = MaterialTheme.typography.bodyMedium)
                            Text(text = "Rating: ${movie.rating}/10", style = MaterialTheme.typography.bodySmall)
                            Text(text = "Description: ${movie.description}", style = MaterialTheme.typography.bodyMedium)
                            Text(text = "Release Date: ${movie.releaseDate}", style = MaterialTheme.typography.bodySmall)

                            // Review input with user name and rating
                            ReviewInput { reviewText, rating, userName ->
                                val review = Review(
                                    movieId = movie.id,  // Movie ID
                                    user = userName,     // User name entered
                                    rating = rating,     // Rating from slider
                                    comment = reviewText // Review text entered
                                )
                                movieViewModel.submitReview(review)
                            }
                        }
                    }
                }
            }
        }

        if (isReviewSubmitted) {
            Toast.makeText(LocalContext.current, "Review Submitted!", Toast.LENGTH_SHORT).show()
        }
    }
}


@Composable
fun ReviewInput(onSubmitReview: (String, Int, String) -> Unit) {
    var reviewText by remember { mutableStateOf("") }
    var rating by remember { mutableStateOf(5) }  // Default rating to 5
    var userName by remember { mutableStateOf("") } // User name

    Column(modifier = Modifier.fillMaxWidth()) {
        Text("Your Name:", style = MaterialTheme.typography.bodyMedium)
        TextField(
            value = userName,
            onValueChange = { userName = it },
            label = { Text("Enter your name") },
            modifier = Modifier.fillMaxWidth(),
            maxLines = 1
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text("Rating: $rating", style = MaterialTheme.typography.bodyMedium)
        Slider(
            value = rating.toFloat(),
            onValueChange = { rating = it.toInt() },
            valueRange = 1f..5f, // Rating between 1 and 5
            steps = 4, // Stops at whole numbers
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text("Write a Review:", style = MaterialTheme.typography.bodyMedium)
        TextField(
            value = reviewText,
            onValueChange = { reviewText = it },
            label = { Text("Your Review") },
            modifier = Modifier.fillMaxWidth(),
            maxLines = 3
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(onClick = {
            if (reviewText.isNotEmpty() && userName.isNotEmpty()) {
                onSubmitReview(reviewText, rating, userName) // Submit review with all data
                reviewText = "" // Clear input after submission
                userName = ""   // Clear user name input after submission
            }
        }) {
            Text("Submit Review")
        }
    }
}


