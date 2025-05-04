package com.example.movieapp.model

data class Movie (
    val id: Int,
    val title: String,
    val description: String,
    val genre: String,
    val director: String,
    val producer: String,
    val rating: Double,
    val pgRating: String,
    val length: Int,
    val releaseDate: String,
    val posterUrl: String,

    val reviews: List<Review>? = null
)

data class Review(
    val movieId: Int,
    val user: String,
    val rating: Int,
    val comment: String
)