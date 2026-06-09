package com.movietablecompose.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.movietablecompose.data.local.PreferencesManager
import com.movietablecompose.domain.model.Movie
import com.movietablecompose.domain.usecase.GetPopularMoviesUseCase
import com.movietablecompose.util.ApiResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber

sealed interface MovieUiState {
    data object Loading : MovieUiState
    data class Success(val movies: List<Movie>) : MovieUiState
    data class Error(val message: String) : MovieUiState
}

class MovieViewModel(
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase,
    private val preferencesManager: PreferencesManager,
    private val logPrefix: String
) : ViewModel() {

    private val _uiState = MutableStateFlow<MovieUiState>(MovieUiState.Loading)
    val uiState: StateFlow<MovieUiState> = _uiState.asStateFlow()

    private val _lastOpenedMovie = MutableStateFlow<String?>(null)
    val lastOpenedMovie: StateFlow<String?> = _lastOpenedMovie.asStateFlow()

    init {
        Timber.d("[$logPrefix] MovieViewModel berhasil diinisialisasi.")
        fetchPopularMovies()
        loadLastOpenedMovie()
    }

    fun fetchPopularMovies() {
        viewModelScope.launch {
            getPopularMoviesUseCase().collect { result ->
                when (result) {
                    is ApiResult.Loading -> {
                        _uiState.value = MovieUiState.Loading
                    }
                    is ApiResult.Success -> {
                        _uiState.value = MovieUiState.Success(result.data)
                        Timber.d("[$logPrefix] Berhasil memancarkan data film, ukuran: ${result.data.size}")
                    }
                    is ApiResult.Error -> {
                        _uiState.value = MovieUiState.Error(result.message)
                        Timber.e("[$logPrefix] Gagal mendapatkan data: ${result.message}")
                    }
                }
            }
        }
    }

    private fun loadLastOpenedMovie() {
        val title = preferencesManager.getLastOpenedMovieTitle()
        _lastOpenedMovie.value = title
        Timber.d("[$logPrefix] SharedPreferences: Film terakhir dibuka -> $title")
    }

    fun saveLastOpenedMovie(id: Int, title: String) {
        preferencesManager.saveLastOpenedMovie(id, title)
        _lastOpenedMovie.value = title
        Timber.d("[$logPrefix] SharedPreferences: Menyimpan film -> $title (ID: $id)")
    }
}