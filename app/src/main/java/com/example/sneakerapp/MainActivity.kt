package com.example.sneakerapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.with
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.sneakerapp.model.Sneaker
import com.example.sneakerapp.ui.*
import com.example.sneakerapp.viewmodel.SneakerViewModel
import com.example.sneakerapp.ui.theme.SneakerAppTheme
import androidx.compose.animation.ExperimentalAnimationApi
import dagger.hilt.android.AndroidEntryPoint
import androidx.navigation.compose.rememberNavController
import com.example.sneakerapp.ui.BottomNav
import com.example.sneakerapp.navigation.SneakerNavGraph

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: SneakerViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SneakerAppTheme {
                val navController = rememberNavController()
                
                Scaffold(
                    bottomBar = {
                        BottomNav(navController = navController)
                    }
                ) { innerPadding ->
                    SneakerNavGraph(
                        navController = navController,
                        viewModel = viewModel,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
} 