package com.example.sneakerapp.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.graphics.Color
import com.example.sneakerapp.ui.theme.DarkRed

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterDialog(
    selectedSizes: Set<Double>,
    priceRange: ClosedFloatingPointRange<Float>,
    onSizeToggle: (Double) -> Unit,
    onPriceRangeChange: (ClosedFloatingPointRange<Float>) -> Unit,
    onDismiss: () -> Unit,
    onClearFilters: () -> Unit
) {
    Dialog(onDismissRequest = onDismiss) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = MaterialTheme.shapes.medium
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "Filter Sneakers",
                    style = MaterialTheme.typography.titleLarge
                )

                Text(
                    text = "Size",
                    style = MaterialTheme.typography.titleMedium
                )

                LazyVerticalGrid(
                    columns = GridCells.Fixed(4),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    val sizes = generateSequence(6.0) { it + 0.5 }
                        .takeWhile { it <= 12.0 }
                        .toList()
                    items(sizes) { size ->
                        FilterChip(
                            selected = size in selectedSizes,
                            onClick = { onSizeToggle(size) },
                            label = { Text(size.toString()) }
                        )
                    }
                }

                Text(
                    text = "Price Range: $${priceRange.start.toInt()} - $${priceRange.endInclusive.toInt()}",
                    style = MaterialTheme.typography.titleMedium
                )

                RangeSlider(
                    value = priceRange,
                    onValueChange = onPriceRangeChange,
                    valueRange = 0f..500f,
                    steps = 20
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Button(
                        onClick = onClearFilters,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = DarkRed,
                            contentColor = Color.White
                        )
                    ) {
                        Text("Clear Filters")
                    }
                    Button(onClick = onDismiss) {
                        Text("Apply")
                    }
                }
            }
        }
    }
} 