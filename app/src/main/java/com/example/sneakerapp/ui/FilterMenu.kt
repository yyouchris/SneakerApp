package com.example.sneakerapp.ui

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.draw.rotate
import com.example.sneakerapp.viewmodel.SneakerViewModel
import com.example.sneakerapp.ui.theme.NavyBlue

@Composable
fun FilterFab(
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit,
    content: @Composable () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        AnimatedVisibility(
            visible = expanded,
            enter = fadeIn() + slideInVertically(
                initialOffsetY = { it },
                animationSpec = tween(300)
            ),
            exit = fadeOut() + slideOutVertically(
                targetOffsetY = { it },
                animationSpec = tween(300)
            )
        ) {
            content()
        }

        val rotation by animateFloatAsState(
            targetValue = if (expanded) 135f else 0f,
            animationSpec = tween(300)
        )

        FloatingActionButton(
            onClick = { onExpandedChange(!expanded) },
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.BottomStart)
                .padding(bottom = 64.dp)
                .rotate(rotation),
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary
        ) {
            Icon(
                imageVector = Icons.Default.Settings,
                contentDescription = if (expanded) "Close filters" else "Show filters"
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterMenu(
    viewModel: SneakerViewModel,
    priceRange: ClosedFloatingPointRange<Float>,
    selectedSizes: Set<Double>,
    onPriceRangeChange: (ClosedFloatingPointRange<Float>) -> Unit,
    onSizeToggle: (Double) -> Unit,
    onClearFilters: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Price Range",
                style = MaterialTheme.typography.titleMedium,
                color = NavyBlue,
                fontWeight = FontWeight.SemiBold
            )
            
            RangeSlider(
                value = priceRange,
                onValueChange = onPriceRangeChange,
                valueRange = 0f..1000f,
                steps = 20
            )
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("$${priceRange.start.toInt()}")
                Text("$${priceRange.endInclusive.toInt()}")
            }

            Spacer(modifier = Modifier.height(16.dp))
            
            Text(
                text = "Sizes",
                style = MaterialTheme.typography.titleMedium,
                color = NavyBlue,
                fontWeight = FontWeight.SemiBold
            )
            
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(vertical = 8.dp)
            ) {
                items(viewModel.availableSizes) { size ->
                    FilterChip(
                        selected = size in selectedSizes,
                        onClick = { onSizeToggle(size) },
                        label = { Text(size.toString()) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = NavyBlue,
                            selectedLabelColor = MaterialTheme.colorScheme.onPrimary
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            
            Button(
                onClick = onClearFilters,
                modifier = Modifier.align(Alignment.End),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.tertiary
                )
            ) {
                Text("Clear Filters")
            }
        }
    }
} 