package com.movietablecompose

import android.app.Application
import com.movietablecompose.data.local.AppDatabase
import com.movietablecompose.data.local.PreferencesManager
import com.movietablecompose.data.remote.ApiClient
import com.movietablecompose.data.repository.MovieRepositoryImpl
import com.movietablecompose.domain.repository.MovieRepository
import com.movietablecompose.domain.usecase.GetPopularMoviesUseCase
import timber.log.Timber

class MovieApplication : Application() {

    lateinit var database: AppDatabase
        private set

    lateinit var preferencesManager: PreferencesManager
        private set

    lateinit var repository: MovieRepository
        private set

    lateinit var getPopularMoviesUseCase: GetPopularMoviesUseCase
        private set

    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())
        Timber.d("MovieApplication: Timber berhasil diinisialisasi")

        database = AppDatabase.getDatabase(this)
        preferencesManager = PreferencesManager(this)

        repository = MovieRepositoryImpl(
            apiService = ApiClient.apiService,
            movieDao = database.movieDao()
        )
        getPopularMoviesUseCase = GetPopularMoviesUseCase(repository)
    }
}