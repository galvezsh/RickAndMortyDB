package com.galvezsh.rickandmortydb.presentation.charactersScreen

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class CharactersViewModel @Inject constructor(): ViewModel() {

    private val _from = MutableStateFlow( 0 )
    val from: StateFlow<Int> = _from

    private val _to = MutableStateFlow( 0 )
    val to: StateFlow<Int> = _to

    private val _searchQuery = MutableStateFlow( "" )
    val searchQuery: StateFlow<String> = _searchQuery

    init {
        //Do the first query here
    }

    fun onFromChanged( from: Int ) { _from.value = from }

    fun onToChanged( to: Int ) { _to.value = to }

    fun onSearchFieldChanged( query: String ) {
        _searchQuery.value = query

        // Update the list with the new query
    }

    fun onGenderFilterChanged( newGender: String ) {
        // Update the list with the new query
    }

    fun onStatusFilterChanged( newStatus: String ) {
        // Update the list with the new query
    }
}