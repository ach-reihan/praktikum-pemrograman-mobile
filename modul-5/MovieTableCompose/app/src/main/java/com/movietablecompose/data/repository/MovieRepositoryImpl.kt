package com.movietablecompose.data.repository

import com.movietablecompose.data.local.MovieDao
import com.movietablecompose.data.mapper.toDomain
import com.movietablecompose.data.mapper.toEntity
import com.movietablecompose.data.remote.MovieApiService
import com.movietablecompose.domain.model.Movie
import com.movietablecompose.domain.repository.MovieRepository
import com.movietablecompose.util.ApiResult
import com.movietablecompose.util.safeApiCall
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber

class MovieRepositoryImpl(
    private val apiService: MovieApiService,
    private val movieDao: MovieDao
) : MovieRepository {

    override fun getPopularMovies(): Flow<ApiResult<List<Movie>>> = flow {
        emit(ApiResult.Loading)

        val cache = movieDao.getAllMovies()
        if (cache.isNotEmpty()) {
            Timber.d("Cache lokal ditemukan. Memancarkan cache ke UI.")
            emit(ApiResult.Success(cache.map { it.toDomain() }))
        }

        Timber.d("Mengambil data terbaru dari API...")
        val result = safeApiCall { apiService.getPopularMovies() }

        when (result) {
            is ApiResult.Success -> {
                val freshMovies = result.data.results.map { it.toDomain() }

                movieDao.clearAllMovies()
                movieDao.insertAll(freshMovies.map { it.toEntity() })

                emit(ApiResult.Success(freshMovies))
            }
            is ApiResult.Error -> {
                if (cache.isEmpty()) {
                    emit(ApiResult.Error(result.message, result.throwable))
                } else {
                    Timber.e(result.throwable, "Gagal mengambil data dari API, tapi menggunakan cache lokal yang ada")
                }
            }
            is ApiResult.Loading -> {
            }
        }
    }
}