package com.example.sneakerapp.model

data class Sneaker(
    val id: String,
    val name: String,
    val price: Double,
    val imageUrl: String,
    val description: String,
    val availableSizes: List<Double>
) 