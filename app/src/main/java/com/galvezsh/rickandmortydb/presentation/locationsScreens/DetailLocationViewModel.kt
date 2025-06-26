package com.galvezsh.rickandmortydb.presentation.locationsScreens

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.galvezsh.rickandmortydb.domain.model.CharacterModel
import com.galvezsh.rickandmortydb.domain.model.LocationModel
import com.galvezsh.rickandmortydb.domain.useCases.GetCharacterByIdUseCase
import com.galvezsh.rickandmortydb.domain.useCases.GetLocationByIdUseCase
import com.galvezsh.rickandmortydb.presentation.shared.extractIdFromUrl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.collections.map

@HiltViewModel
class DetailLocationViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    locationUseCase: GetLocationByIdUseCase,
    private val characterUseCase: GetCharacterByIdUseCase
): ViewModel() {

    private val _locationId: Int = savedStateHandle[ "locationId" ] ?: -1

    private val _location = MutableStateFlow<LocationModel?>( LocationModel.empty() )
    val location: StateFlow<LocationModel?> = _location

    private val _characterTexts = MutableStateFlow( emptyList<String>() )
    val characterTexts: StateFlow<List<String>> = _characterTexts

    init {
        viewModelScope.launch( Dispatchers.IO ) {
            _location.value = locationUseCase( _locationId )
        }
    }

    fun loadCharacters( urls: List<String> ) {
        viewModelScope.launch( Dispatchers.IO ) {
            val texts = urls.map { url ->
                val id = extractIdFromUrl( url )!!
                val character = characterUseCase( id ) ?: CharacterModel.empty()
                character.name
            }
            _characterTexts.value = texts
        }
    }

}