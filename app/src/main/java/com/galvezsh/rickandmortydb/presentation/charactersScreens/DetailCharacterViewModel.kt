package com.galvezsh.rickandmortydb.presentation.charactersScreens

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.galvezsh.rickandmortydb.domain.model.CharacterModel
import com.galvezsh.rickandmortydb.domain.model.EpisodeModel
import com.galvezsh.rickandmortydb.domain.useCases.GetCharacterByIdUseCase
import com.galvezsh.rickandmortydb.domain.useCases.GetEpisodeByIdUseCase
import com.galvezsh.rickandmortydb.presentation.shared.extractIdFromUrl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailCharacterViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    characterUseCase: GetCharacterByIdUseCase,
    private val episodeUseCase: GetEpisodeByIdUseCase
    ): ViewModel() {

    private val _characterId: Int = savedStateHandle[ "characterId" ] ?: -1

    private val _character = MutableStateFlow<CharacterModel?>( CharacterModel.empty() )
    val character: StateFlow<CharacterModel?> = _character

    private val _episodeTexts = MutableStateFlow<List<String>>( emptyList() )
    val episodeTexts: StateFlow<List<String>> = _episodeTexts

    init {
        viewModelScope.launch( Dispatchers.IO ) {
            _character.value = characterUseCase( _characterId )
        }
    }

    fun loadEpisodes( urls: List<String> ) {
        viewModelScope.launch( Dispatchers.IO ) {
            val texts = urls.map { url ->
                val id = extractIdFromUrl( url )!!
                val episode = episodeUseCase( id ) ?: EpisodeModel.empty()
                "${episode.episode}: ${episode.name}"
            }
            _episodeTexts.value = texts
        }
    }
}