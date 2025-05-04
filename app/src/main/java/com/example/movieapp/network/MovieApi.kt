package com.example.movieapp.network

import com.example.movieapp.model.Movie
import com.example.movieapp.model.Review
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface MovieApi {

    @GET("api/movies")
    suspend fun getMovies(): Response<List<Movie>>

    @POST("api/reviews")
    suspend fun submitReview(@Body review: Review): Response<Unit>
}
