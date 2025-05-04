package com.example.movieapp.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.model.Movie
import com.example.movieapp.model.Review
import com.example.movieapp.repository.MovieRepository
import kotlinx.coroutines.launch
import retrofit2.Response

class MovieViewModel : ViewModel() {

    private val repository = MovieRepository()

    private val _movies = mutableStateOf<List<Movie>>(emptyList())
    val movies: State<List<Movie>> get() = _movies

    private val _error = mutableStateOf<String?>(null)
    val error: State<String?> get() = _error

    private val _isReviewSubmitted = mutableStateOf<Boolean>(false)
    val isReviewSubmitted: State<Boolean> get() = _isReviewSubmitted

    // Fetch movies using a coroutine
    fun fetchMovies() {
        // Use viewModelScope to launch a coroutine
        viewModelScope.launch {
            try {
                // Fetch movies from repository
                val response: Response<List<Movie>> = repository.getMovies()

                if (response.isSuccessful) {
                    val moviesList = response.body() ?: emptyList()
                    println("DEBUG: Received ${moviesList.size} movies")
                    _movies.value = moviesList
                } else {
                    println("DEBUG: Response not successful: ${response.code()}")
                    _error.value = "Failed to fetch movies"
                }

            } catch (e: Exception) {
                println("DEBUG: Exception during fetchMovies: ${e.message}")
                _error.value = e.message
            }
        }
    }

    // Submit a review using a coroutine
    fun submitReview(review: Review) {
        viewModelScope.launch {
            try {
                val response = repository.submitReview(review)

                if (response.isSuccessful) {
                    _isReviewSubmitted.value = true
                } else {
                    // Log the response for debugging
                    _error.value = "Failed to submit review. Error: ${response.message()}"
                    _isReviewSubmitted.value = false
                }
            } catch (e: Exception) {
                _error.value = "Exception: ${e.message}"
                _isReviewSubmitted.value = false
            }
        }
    }
}
