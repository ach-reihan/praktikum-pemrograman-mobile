package com.movietablecompose.ui.navigation

import android.content.Intent
import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.movietablecompose.MovieApplication
import com.movietablecompose.ui.screen.DetailScreen
import com.movietablecompose.ui.screen.ListScreen
import com.movietablecompose.ui.viewmodel.MovieUiState
import com.movietablecompose.ui.viewmodel.MovieViewModel
import com.movietablecompose.ui.viewmodel.MovieViewModelFactory
import androidx.core.net.toUri

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val context = LocalContext.current
    val app = context.applicationContext as MovieApplication

    val viewModel: MovieViewModel = viewModel(
        factory = MovieViewModelFactory(
            getPopularMoviesUseCase = app.getPopularMoviesUseCase,
            preferencesManager = app.preferencesManager,
            logPrefix = "MovieAppLog"
        )
    )

    val uiState by viewModel.uiState.collectAsState()
    val lastOpenedMovie by viewModel.lastOpenedMovie.collectAsState()

    NavHost(navController = navController, startDestination = "list_screen") {
        composable("list_screen") {
            ListScreen(
                uiState = uiState,
                lastOpenedMovie = lastOpenedMovie,
                onWebClick = { url ->
                    val intent = Intent(Intent.ACTION_VIEW, url.toUri())
                    context.startActivity(intent)
                },
                onDetailClick = { movie ->
                    viewModel.saveLastOpenedMovie(movie.id, movie.title)
                    navController.navigate("detail_screen/${movie.id}")
                },
                onRetryClick = {
                    viewModel.fetchPopularMovies()
                }
            )
        }

        composable(
            route = "detail_screen/{movieId}",
            arguments = listOf(navArgument("movieId") { type = NavType.IntType })
        ) { backStackEntry ->
            val movieId = backStackEntry.arguments?.getInt("movieId") ?: -1

            val movie = (uiState as? MovieUiState.Success)?.movies?.find { it.id == movieId }

            if (movie != null) {
                DetailScreen(movie = movie, onBackClick = { navController.popBackStack() })
            }
        }
    }
}