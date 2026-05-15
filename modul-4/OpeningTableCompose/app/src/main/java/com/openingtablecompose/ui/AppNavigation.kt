package com.openingtablecompose.ui

import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.net.toUri
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.openingtablecompose.data.OpeningRepository
import com.openingtablecompose.ui.viewmodel.OpeningEvent
import com.openingtablecompose.ui.viewmodel.OpeningViewModel
import com.openingtablecompose.ui.viewmodel.OpeningViewModelFactory

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val context = LocalContext.current

    NavHost(navController = navController, startDestination = "list_screen") {

        composable("list_screen") {
            val viewModel: OpeningViewModel = viewModel(
                factory = OpeningViewModelFactory("ComposeLog")
            )

            val openings by viewModel.openings.collectAsState()

            val navigationEvent by viewModel.navigationEvent.collectAsState()

            LaunchedEffect(navigationEvent) {
                when (val event = navigationEvent) {
                    is OpeningEvent.NavigateToDetail -> {
                        navController.navigate("detail_screen/${event.id}")
                        viewModel.onEventHandled()
                    }
                    is OpeningEvent.NavigateToWeb -> {
                        val intent = Intent(Intent.ACTION_VIEW, event.url.toUri())
                        context.startActivity(intent)
                        viewModel.onEventHandled()
                    }
                    null -> { }
                }
            }

            ListScreen(
                openings = openings,
                onWebClick = { url ->
                    viewModel.onWebClicked(url)
                },
                onDetailClick = { opening ->
                    viewModel.onDetailClicked(opening)
                }
            )
        }

        composable(
            route = "detail_screen/{openingId}",
            arguments = listOf(navArgument("openingId") { type = NavType.StringType })
        ) { backStackEntry ->
            val openingId = backStackEntry.arguments?.getString("openingId")
            val opening = OpeningRepository.getOpenings().find { it.id == openingId }

            if (opening != null) {
                DetailScreen(opening = opening)
            }
        }
    }
}