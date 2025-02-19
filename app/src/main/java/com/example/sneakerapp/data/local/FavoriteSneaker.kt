package com.example.sneakerapp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_sneakers")
data class FavoriteSneaker(
    @PrimaryKey
    val id: String
) 