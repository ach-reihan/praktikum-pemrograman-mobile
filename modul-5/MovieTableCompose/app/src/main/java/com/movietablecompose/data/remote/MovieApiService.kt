package com.movietablecompose.data.remote

import com.movietablecompose.data.model.dto.MovieResponseDto
import retrofit2.http.GET

interface MovieApiService {
    @GET("movie/popular")
    suspend fun getPopularMovies(): MovieResponseDto
}