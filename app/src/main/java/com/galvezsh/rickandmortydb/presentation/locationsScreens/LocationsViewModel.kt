package com.galvezsh.rickandmortydb.presentation.locationsScreens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.galvezsh.rickandmortydb.domain.useCases.GetCountOfLocationsFlowUseCase
import com.galvezsh.rickandmortydb.domain.useCases.GetLocationsFlowUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.collections.component1
import kotlin.collections.component2

@HiltViewModel
class LocationsViewModel @Inject constructor(
    getLocationsFlowUseCase: GetLocationsFlowUseCase,
    getCountOfLocationsFlowUseCase: GetCountOfLocationsFlowUseCase
): ViewModel() {

    private val _from = MutableStateFlow( 0 )
    val from: StateFlow<Int> = _from

    private val _to = MutableStateFlow( 0 )
    val to: StateFlow<Int> = _to

    private val _selectedIndexType = MutableStateFlow( 0 )
    val selectedIndexType: StateFlow<Int> = _selectedIndexType

    private val _searchQuery = MutableStateFlow( "" )
    val searchQuery: StateFlow<String> = _searchQuery

    private val _type = MutableStateFlow( "" )
    val type: StateFlow<String> = _type

    private val filters = combine( _searchQuery, _type ) { (name, type) ->
        Pair( name, type )
    }

    val locations = filters.flatMapLatest { (name, type) ->
        getLocationsFlowUseCase( name, type )
    }

    init {
        viewModelScope.launch {
            getCountOfLocationsFlowUseCase().collect { _to.value = it }
        }
    }

    fun onFromChanged( from: Int ) {
        _from.value = from
    }

    fun onTypeFilterChanged( newType: String, newIndex: Int ) {
        _type.value = newType
        _selectedIndexType.value = newIndex
    }

    fun onSearchFieldChanged( query: String ) {
        _searchQuery.value = query
    }
}