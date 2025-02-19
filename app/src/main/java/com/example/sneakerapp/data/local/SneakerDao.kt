package com.example.sneakerapp.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface SneakerDao {
    @Query("SELECT EXISTS(SELECT 1 FROM favorite_sneakers WHERE id = :sneakerId)")
    fun isFavorite(sneakerId: String): Flow<Boolean>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToFavorites(favoriteSneaker: FavoriteSneaker)

    @Delete
    suspend fun removeFromFavorites(favoriteSneaker: FavoriteSneaker)

    @Query("SELECT * FROM favorite_sneakers")
    fun getAllFavorites(): Flow<List<FavoriteSneaker>>
} 