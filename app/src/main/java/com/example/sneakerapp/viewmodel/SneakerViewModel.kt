package com.example.sneakerapp.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sneakerapp.data.repository.SneakerRepository
import com.example.sneakerapp.model.Sneaker
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SneakerViewModel @Inject constructor(
    private val repository: SneakerRepository
) : ViewModel() {
    val sneakers: List<Sneaker> = repository.getAllSneakers()
    
    // Add search query state
    private var searchQuery by mutableStateOf("")
    
    // Initialize filteredSneakers as null initially
    private var _filteredSneakers by mutableStateOf<List<Sneaker>?>(null)
    var filteredSneakers: List<Sneaker>
        get() = _filteredSneakers ?: sneakers
        private set(value) {
            _filteredSneakers = value
        }

    val availableSizes = sneakers.flatMap { it.availableSizes }.distinct().sorted()
    var selectedSizes by mutableStateOf<Set<Double>>(emptySet())
        private set
    
    var priceRange by mutableStateOf(0f..500f)
        private set

    private val _favoriteSneakers = MutableStateFlow<List<Sneaker>>(emptyList())
    val favoriteSneakers: StateFlow<List<Sneaker>> = _favoriteSneakers.asStateFlow()

    private val _favoriteIds = MutableStateFlow<Set<String>>(emptySet())
    private val favoriteStates = mutableMapOf<String, StateFlow<Boolean>>()

    init {
        // Load initial favorite states
        viewModelScope.launch {
            // First collect initial favorites
            val initialFavorites = repository.getFavoriteSneakers().first()
            _favoriteSneakers.value = initialFavorites
            _favoriteIds.value = initialFavorites.map { it.id }.toSet()

            // Then start collecting updates
            repository.getFavoriteSneakers().collect { favorites ->
                _favoriteSneakers.value = favorites
                _favoriteIds.value = favorites.map { it.id }.toSet()
            }
        }

        // Initialize favorite states for all sneakers
        sneakers.forEach { sneaker ->
            favoriteStates[sneaker.id] = _favoriteIds
                .map { ids -> sneaker.id in ids }
                .stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.Eagerly,
                    initialValue = false
                )
        }
    }

    fun toggleFavorite(sneaker: Sneaker) {
        viewModelScope.launch {
            // First update local state for immediate UI feedback
            _favoriteIds.value = if (sneaker.id in _favoriteIds.value) {
                _favoriteIds.value - sneaker.id
            } else {
                _favoriteIds.value + sneaker.id
            }
            
            // Then update the database
            repository.toggleFavorite(sneaker.id)
        }
    }

    fun isSneakerFavorite(sneakerId: String): StateFlow<Boolean> =
        favoriteStates.getOrPut(sneakerId) {
            _favoriteIds
                .map { ids -> sneakerId in ids }
                .stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.Eagerly,
                    initialValue = false
                )
        }

    fun updatePriceRange(range: ClosedFloatingPointRange<Float>) {
        priceRange = range
        updateFilteredSneakers()
    }

    fun toggleSizeFilter(size: Double) {
        selectedSizes = if (size in selectedSizes) {
            selectedSizes - size
        } else {
            selectedSizes + size
        }
        updateFilteredSneakers()
    }

    fun updateSearchQuery(query: String) {
        searchQuery = query
        updateFilteredSneakers()
    }

    private fun updateFilteredSneakers() {
        // If no filters are active, reset to showing all sneakers
        if (searchQuery.isEmpty() && selectedSizes.isEmpty() && priceRange == (0f..500f)) {
            _filteredSneakers = null
            return
        }

        filteredSneakers = sneakers.filter { sneaker ->
            val matchesSearch = sneaker.name.contains(searchQuery, ignoreCase = true)
            val priceInRange = sneaker.price.toFloat() in priceRange
            val sizeMatches = selectedSizes.isEmpty() || 
                sneaker.availableSizes.any { it in selectedSizes }
            
            matchesSearch && priceInRange && sizeMatches
        }
    }

    fun clearFilters() {
        selectedSizes = emptySet()
        priceRange = 0f..500f
        searchQuery = ""
        _filteredSneakers = null
    }
} 