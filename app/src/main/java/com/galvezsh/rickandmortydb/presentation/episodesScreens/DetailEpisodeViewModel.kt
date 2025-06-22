package com.galvezsh.rickandmortydb.presentation.episodesScreens

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.galvezsh.rickandmortydb.domain.model.EpisodeModel
import com.galvezsh.rickandmortydb.domain.useCases.GetEpisodeByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailEpisodeViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    useCase: GetEpisodeByIdUseCase
): ViewModel() {
    private val _episodeId: Int = savedStateHandle[ "episodeId" ] ?: -1

    private val _episode = MutableStateFlow<EpisodeModel?>( EpisodeModel.empty() )
    val episode: StateFlow<EpisodeModel?> = _episode

    init {
        viewModelScope.launch( Dispatchers.IO ) {
            _episode.value = useCase( _episodeId )
        }
    }
}