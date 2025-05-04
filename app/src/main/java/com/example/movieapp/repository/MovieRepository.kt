package com.example.movieapp.repository

import com.example.movieapp.model.Movie
import com.example.movieapp.model.Review
import com.example.movieapp.network.ApiClient
import com.example.movieapp.network.MovieApi
import retrofit2.Response

class MovieRepository {

    private val movieApi = ApiClient().retrofit.create(MovieApi::class.java)

    suspend fun getMovies(): Response<List<Movie>> {
        return movieApi.getMovies()
    }

    suspend fun submitReview(review: Review): Response<Unit> {
        return movieApi.submitReview(review)
    }
}

