package com.movietablecompose.domain.usecase

import com.movietablecompose.domain.model.Movie
import com.movietablecompose.domain.repository.MovieRepository
import com.movietablecompose.util.ApiResult
import kotlinx.coroutines.flow.Flow

class GetPopularMoviesUseCase(private val repository: MovieRepository) {
    operator fun invoke(): Flow<ApiResult<List<Movie>>> {
        return repository.getPopularMovies()
    }
}