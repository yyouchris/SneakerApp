package com.example.sneakerapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.sneakerapp.model.Sneaker
import com.example.sneakerapp.ui.HomeScreen
import com.example.sneakerapp.ui.FavoritesScreen
import com.example.sneakerapp.ui.SneakerDetailScreen
import com.example.sneakerapp.viewmodel.SneakerViewModel
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.padding
import com.example.sneakerapp.ui.util.enterTransition
import com.example.sneakerapp.ui.util.exitTransition
import com.example.sneakerapp.ui.util.popEnterTransition
import com.example.sneakerapp.ui.util.popExitTransition
import com.example.sneakerapp.ui.util.detailEnterTransition

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Favorites : Screen("favorites")
    object Detail : Screen("detail/{sneakerId}") {
        fun createRoute(sneakerId: String) = "detail/$sneakerId"
    }
}

@Composable
fun SneakerNavGraph(
    navController: NavHostController,
    viewModel: SneakerViewModel,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route,
        modifier = modifier,
        enterTransition = { enterTransition() },
        exitTransition = { exitTransition() },
        popEnterTransition = { popEnterTransition() },
        popExitTransition = { popExitTransition() }
    ) {
        composable(
            route = Screen.Home.route,
            enterTransition = { enterTransition() },
            exitTransition = { exitTransition() }
        ) {
            HomeScreen(
                sneakers = viewModel.filteredSneakers,
                onSneakerClick = { sneaker ->
                    navController.navigate(Screen.Detail.createRoute(sneaker.id))
                },
                onFavoriteClick = viewModel::toggleFavorite,
                isFavorite = { sneakerId ->
                    viewModel.isSneakerFavorite(sneakerId)
                },
                onSearchQueryChange = viewModel::updateSearchQuery,
                selectedSizes = viewModel.selectedSizes,
                priceRange = viewModel.priceRange,
                onSizeToggle = viewModel::toggleSizeFilter,
                onPriceRangeChange = viewModel::updatePriceRange,
                onClearFilters = viewModel::clearFilters
            )
        }
        
        composable(
            route = Screen.Favorites.route,
            enterTransition = { enterTransition() },
            exitTransition = { exitTransition() }
        ) {
            FavoritesScreen(
                sneakers = viewModel.favoriteSneakers.collectAsState().value,
                onSneakerClick = { sneaker ->
                    navController.navigate(Screen.Detail.createRoute(sneaker.id))
                },
                onFavoriteClick = viewModel::toggleFavorite,
                isFavorite = { sneakerId ->
                    viewModel.isSneakerFavorite(sneakerId)
                }
            )
        }
        
        composable(
            route = Screen.Detail.route,
            arguments = listOf(navArgument("sneakerId") { type = NavType.StringType }),
            enterTransition = { detailEnterTransition() },
            exitTransition = { exitTransition() }
        ) { backStackEntry ->
            val sneakerId = backStackEntry.arguments?.getString("sneakerId")
            sneakerId?.let { id ->
                val sneaker = viewModel.sneakers.find { it.id == id }
                sneaker?.let {
                    SneakerDetailScreen(
                        sneaker = it,
                        onBackClick = { navController.popBackStack() },
                        onFavoriteClick = viewModel::toggleFavorite,
                        isFavorite = { sneakerId -> 
                            viewModel.isSneakerFavorite(sneakerId)
                        }
                    )
                }
            }
        }
    }
} 