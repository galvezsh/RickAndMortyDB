package com.galvezsh.rickandmortydb.presentation.episodesScreens

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.galvezsh.rickandmortydb.domain.model.CharacterModel
import com.galvezsh.rickandmortydb.domain.model.EpisodeModel
import com.galvezsh.rickandmortydb.domain.useCases.GetCharacterByIdUseCase
import com.galvezsh.rickandmortydb.domain.useCases.GetEpisodeByIdUseCase
import com.galvezsh.rickandmortydb.presentation.extractIdFromUrl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailEpisodeViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    episodeUseCase: GetEpisodeByIdUseCase,
    private val characterUseCase: GetCharacterByIdUseCase
): ViewModel() {
    private val _episodeId: Int = savedStateHandle[ "episodeId" ] ?: -1

    private val _episode = MutableStateFlow<EpisodeModel?>( EpisodeModel.empty() )
    val episode: StateFlow<EpisodeModel?> = _episode

    private val _characterTexts = MutableStateFlow( emptyList<String>() )
    val characterTexts: StateFlow<List<String>> = _characterTexts

    init {
        viewModelScope.launch( Dispatchers.IO ) {
            _episode.value = episodeUseCase( _episodeId )
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