package com.example.sneakerapp.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import kotlinx.coroutines.flow.StateFlow
import com.example.sneakerapp.model.Sneaker

@Composable
fun FavoritesScreen(
    sneakers: List<Sneaker>,
    onSneakerClick: (Sneaker) -> Unit,
    onFavoriteClick: (Sneaker) -> Unit,
    isFavorite: (String) -> StateFlow<Boolean>
) {
    SneakerGrid(
        sneakers = sneakers,
        onSneakerClick = onSneakerClick,
        onFavoriteClick = onFavoriteClick,
        isFavorite = isFavorite
    )
} 