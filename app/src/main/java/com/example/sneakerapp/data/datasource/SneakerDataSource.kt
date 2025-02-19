package com.example.sneakerapp.data.datasource

import com.example.sneakerapp.data.SneakerData
import com.example.sneakerapp.model.Sneaker
import javax.inject.Inject

interface SneakerDataSource {
    fun getSneakers(): List<Sneaker>
}

class LocalSneakerDataSource @Inject constructor() : SneakerDataSource {
    override fun getSneakers(): List<Sneaker> = SneakerData.sneakers
} 