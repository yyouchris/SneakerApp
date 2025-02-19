package com.example.sneakerapp.data.repository

import com.example.sneakerapp.data.datasource.SneakerDataSource
import com.example.sneakerapp.model.Sneaker
import com.example.sneakerapp.data.local.SneakerDao
import com.example.sneakerapp.data.local.FavoriteSneaker
import kotlinx.coroutines.flow.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SneakerRepository @Inject constructor(
    private val dataSource: SneakerDataSource,
    private val sneakerDao: SneakerDao
) {
    fun getAllSneakers(): List<Sneaker> = dataSource.getSneakers()

    suspend fun toggleFavorite(sneakerId: String) {
        val isFavorite = sneakerDao.isFavorite(sneakerId).first()
        if (isFavorite) {
            sneakerDao.removeFromFavorites(FavoriteSneaker(sneakerId))
        } else {
            sneakerDao.addToFavorites(FavoriteSneaker(sneakerId))
        }
    }

    fun isSneakerFavorite(sneakerId: String): Flow<Boolean> =
        sneakerDao.isFavorite(sneakerId)

    fun getFavoriteSneakers(): Flow<List<Sneaker>> =
        sneakerDao.getAllFavorites()
            .map { favorites ->
                dataSource.getSneakers().filter { sneaker ->
                    favorites.any { it.id == sneaker.id }
                }
            }
} 