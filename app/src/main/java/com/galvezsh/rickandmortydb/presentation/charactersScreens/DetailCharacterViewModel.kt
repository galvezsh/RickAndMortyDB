package com.galvezsh.rickandmortydb.presentation.charactersScreens

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.galvezsh.rickandmortydb.domain.model.CharacterModel
import com.galvezsh.rickandmortydb.domain.useCases.GetCharacterByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailCharacterViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    useCase: GetCharacterByIdUseCase
): ViewModel() {

    private val _characterId: Int = savedStateHandle[ "characterId" ] ?: -1

    private val _character = MutableStateFlow<CharacterModel?>( CharacterModel.empty() )
    val character: StateFlow<CharacterModel?> = _character

    init {
        viewModelScope.launch( Dispatchers.IO ) {
            _character.value = useCase( _characterId )
        }
    }
}