package com.openingtablecompose.ui

import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.openingtablecompose.data.OpeningRepository
import androidx.core.net.toUri

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val context = LocalContext.current
    val openings = OpeningRepository.getOpenings()

    NavHost(navController = navController, startDestination = "list_screen") {

        composable("list_screen") {
            ListScreen(
                openings = openings,
                onWebClick = { url ->
                    val intent = Intent(Intent.ACTION_VIEW, url.toUri())
                    context.startActivity(intent)
                },
                onDetailClick = { id ->
                    navController.navigate("detail_screen/$id")
                }
            )
        }

        composable(
            route = "detail_screen/{openingId}",
            arguments = listOf(navArgument("openingId") { type = NavType.StringType })
        ) { backStackEntry ->
            val openingId = backStackEntry.arguments?.getString("openingId")
            val opening = openings.find { it.id == openingId }

            if (opening != null) {
                DetailScreen(opening = opening)
            }
        }
    }
}