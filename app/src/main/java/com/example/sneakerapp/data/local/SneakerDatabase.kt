package com.example.sneakerapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [FavoriteSneaker::class], 
    version = 1,
    exportSchema = false
)
abstract class SneakerDatabase : RoomDatabase() {
    abstract fun sneakerDao(): SneakerDao
} 