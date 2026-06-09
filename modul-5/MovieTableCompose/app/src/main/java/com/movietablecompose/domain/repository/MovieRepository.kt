package com.movietablecompose.domain.repository

import com.movietablecompose.domain.model.Movie
import com.movietablecompose.util.ApiResult
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    fun getPopularMovies(): Flow<ApiResult<List<Movie>>>
}