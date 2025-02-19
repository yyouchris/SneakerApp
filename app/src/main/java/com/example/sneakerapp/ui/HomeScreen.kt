package com.example.sneakerapp.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.StateFlow
import com.example.sneakerapp.model.Sneaker

@Composable
fun HomeScreen(
    sneakers: List<Sneaker>,
    onSneakerClick: (Sneaker) -> Unit,
    onFavoriteClick: (Sneaker) -> Unit,
    isFavorite: (String) -> StateFlow<Boolean>,
    selectedSizes: Set<Double> = emptySet(),
    priceRange: ClosedFloatingPointRange<Float> = 0f..1000f,
    onSizeToggle: (Double) -> Unit = {},
    onPriceRangeChange: (ClosedFloatingPointRange<Float>) -> Unit = {},
    onClearFilters: () -> Unit = {},
    onSearchQueryChange: (String) -> Unit
) {
    var showFilterDialog by remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {
        Column {
            SearchBar(
                onQueryChange = onSearchQueryChange
            )
            SneakerGrid(
                sneakers = sneakers,
                onSneakerClick = onSneakerClick,
                onFavoriteClick = onFavoriteClick,
                isFavorite = isFavorite
            )
        }

        FloatingActionButton(
            onClick = { showFilterDialog = true },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(
                    end = 16.dp,
                    bottom = 80.dp  // Increased bottom padding to account for navigation bar
                )
        ) {
            Icon(
                imageVector = Icons.Default.List,
                contentDescription = "Filter"
            )
        }

        if (showFilterDialog) {
            FilterDialog(
                selectedSizes = selectedSizes,
                priceRange = priceRange,
                onSizeToggle = onSizeToggle,
                onPriceRangeChange = onPriceRangeChange,
                onDismiss = { showFilterDialog = false },
                onClearFilters = onClearFilters
            )
        }
    }
} 