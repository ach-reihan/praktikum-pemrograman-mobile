package com.movietablecompose.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.movietablecompose.data.local.PreferencesManager
import com.movietablecompose.domain.usecase.GetPopularMoviesUseCase

class MovieViewModelFactory(
    private val getPopularMoviesUseCase: GetPopularMoviesUseCase,
    private val preferencesManager: PreferencesManager,
    private val logPrefix: String
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MovieViewModel::class.java)) {
            return MovieViewModel(getPopularMoviesUseCase, preferencesManager, logPrefix) as T
        }
        throw IllegalArgumentException("Kelas ViewModel tidak dikenal: ${modelClass.name}")
    }
}