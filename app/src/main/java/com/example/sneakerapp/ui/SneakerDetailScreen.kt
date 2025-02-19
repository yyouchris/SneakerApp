package com.example.sneakerapp.ui

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.background
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.sneakerapp.model.Sneaker
import com.example.sneakerapp.ui.theme.DarkRed
import kotlinx.coroutines.flow.StateFlow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SneakerDetailScreen(
    sneaker: Sneaker,
    onBackClick: () -> Unit,
    onFavoriteClick: (Sneaker) -> Unit,
    isFavorite: (String) -> StateFlow<Boolean>
) {
    var isImageLoaded by remember { mutableStateOf(false) }
    val isFavoriteState by isFavorite(sneaker.id).collectAsState(initial = false)
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Sneaker Details") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { onFavoriteClick(sneaker) }) {
                        Icon(
                            imageVector = if (isFavoriteState) {
                                Icons.Default.Favorite
                            } else {
                                Icons.Default.FavoriteBorder
                            },
                            contentDescription = if (isFavoriteState) {
                                "Remove from favorites"
                            } else {
                                "Add to favorites"
                            },
                            tint = if (isFavoriteState) DarkRed else MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
            ) {
                // Image first
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(sneaker.imageUrl)
                        .crossfade(true)
                        .build(),
                    contentDescription = sneaker.name,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp),
                    contentScale = ContentScale.Fit,
                    onSuccess = { isImageLoaded = true }
                )
            }
            
            AnimatedVisibility(
                visible = isImageLoaded,
                enter = fadeIn(animationSpec = tween(300)) +
                        slideInVertically(
                            animationSpec = spring(
                                dampingRatio = Spring.DampingRatioMediumBouncy,
                                stiffness = Spring.StiffnessLow
                            )
                        )
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Spacer(modifier = Modifier.height(24.dp))
                    
                    Text(
                        text = sneaker.name,
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold
                    )
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    Text(
                        text = String.format("$%.2f", sneaker.price),
                        style = MaterialTheme.typography.headlineMedium,
                        color = DarkRed,
                        fontWeight = FontWeight.Bold
                    )
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    Text(
                        text = sneaker.description,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    Text(
                        text = "Available Sizes",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.SemiBold
                    )
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        sneaker.availableSizes.forEach { size ->
                            Text(
                                text = size.toString(),
                                modifier = Modifier.padding(horizontal = 8.dp),
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                    }
                }
            }
        }
    }
} 