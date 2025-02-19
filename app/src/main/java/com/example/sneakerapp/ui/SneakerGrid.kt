package com.example.sneakerapp.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.foundation.background
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.sneakerapp.model.Sneaker
import com.example.sneakerapp.ui.theme.DarkRed
import kotlinx.coroutines.flow.StateFlow

@Composable
fun SneakerGrid(
    sneakers: List<Sneaker>,
    onSneakerClick: (Sneaker) -> Unit,
    onFavoriteClick: (Sneaker) -> Unit,
    isFavorite: (String) -> StateFlow<Boolean>,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        contentPadding = PaddingValues(
            start = 12.dp,
            end = 12.dp,
            top = 16.dp,
            bottom = 80.dp
        ),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
    ) {
        items(sneakers) { sneaker ->
            SneakerCard(
                sneaker = sneaker,
                onClick = { onSneakerClick(sneaker) },
                onFavoriteClick = { onFavoriteClick(sneaker) },
                isFavorite = isFavorite(sneaker.id)
            )
        }
    }
}

@Composable
fun SneakerCard(
    sneaker: Sneaker,
    onClick: () -> Unit,
    onFavoriteClick: () -> Unit,
    isFavorite: StateFlow<Boolean>
) {
    val isFavoriteState by isFavorite.collectAsState()

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(280.dp)
            .padding(4.dp)
            .clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 4.dp
        )
    ) {
        Box {
            Column {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(180.dp)
                ) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(sneaker.imageUrl)
                            .crossfade(true)
                            .build(),
                        contentDescription = sneaker.name,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(180.dp),
                        contentScale = ContentScale.Fit
                    )
                }
                Column(
                    modifier = Modifier
                        .padding(12.dp)
                        .fillMaxHeight(),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = sneaker.name,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontSize = 16.sp
                        )
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = String.format("$%.2f", sneaker.price),
                        style = MaterialTheme.typography.bodyLarge,
                        color = DarkRed,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            IconButton(
                onClick = onFavoriteClick,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(8.dp)
            ) {
                Icon(
                    imageVector = if (isFavoriteState) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                    contentDescription = if (isFavoriteState) "Remove from favorites" else "Add to favorites",
                    tint = if (isFavoriteState) MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
            }
        }
    }
} 