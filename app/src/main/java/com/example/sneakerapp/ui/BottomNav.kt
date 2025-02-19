package com.example.sneakerapp.ui

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import com.example.sneakerapp.ui.theme.NavyBlue
import com.example.sneakerapp.ui.theme.LightRed
import androidx.navigation.NavHostController
import com.example.sneakerapp.navigation.Screen
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier

@Composable
fun BottomNav(navController: NavHostController) {
    NavigationBar(
        modifier = Modifier.animateContentSize(
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessLow
            )
        )
    ) {
        NavigationBarItem(
            selected = navController.currentDestination?.route == Screen.Home.route,
            onClick = {
                navController.navigate(Screen.Home.route) {
                    launchSingleTop = true
                    restoreState = true
                }
            },
            icon = {
                AnimatedVisibility(
                    visible = true,
                    enter = fadeIn() + scaleIn(),
                    exit = fadeOut() + scaleOut()
                ) {
                    Icon(Icons.Default.Home, "Home")
                }
            },
            label = {
                AnimatedVisibility(
                    visible = true,
                    enter = expandVertically() + fadeIn(),
                    exit = shrinkVertically() + fadeOut()
                ) {
                    Text("Home")
                }
            }
        )
        NavigationBarItem(
            selected = navController.currentDestination?.route == Screen.Favorites.route,
            onClick = { navController.navigate(Screen.Favorites.route) },
            icon = { Icon(Icons.Default.Favorite, "Favorites") },
            label = { Text("Favorites") }
        )
    }
} 