package com.galvezsh.rickandmortydb.presentation.episodesScreens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.galvezsh.rickandmortydb.domain.useCases.GetCountOfEpisodesFlowUseCase
import com.galvezsh.rickandmortydb.domain.useCases.GetEpisodesFlowUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EpisodesViewModel @Inject constructor(
    getEpisodesFlowUseCase: GetEpisodesFlowUseCase,
    getCountOfEpisodesFlowUseCase: GetCountOfEpisodesFlowUseCase
    ): ViewModel() {

    private val _from = MutableStateFlow( 0 )
    val from: StateFlow<Int> = _from

    private val _to = MutableStateFlow( 0 )
    val to: StateFlow<Int> = _to

    private val _searchQuery = MutableStateFlow( "" )
    val searchQuery: StateFlow<String> = _searchQuery

    private val _season = MutableStateFlow( "" )

    private val _seasonIndex = MutableStateFlow( 0 )
    val seasonIndex: StateFlow<Int> = _seasonIndex

    private val filters = combine( _searchQuery, _season ) { (name, season) ->
        Pair( name, season )
    }

    val episodes = filters.flatMapLatest { (name, season) ->
        getEpisodesFlowUseCase( name, season )
    }.flowOn( Dispatchers.Default )

    init {
        viewModelScope.launch {
            getCountOfEpisodesFlowUseCase().collect { _to.value = it }
        }
    }

    fun onFromChanged( from: Int ) {
        _from.value = from
    }

    fun onSearchFieldChanged( query: String ) {
        _searchQuery.value = query
    }

    fun onSeasonFilterChanged( newSeason: String, newIndex: Int ) {
        _season.value = newSeason
        _seasonIndex.value = newIndex
    }
}