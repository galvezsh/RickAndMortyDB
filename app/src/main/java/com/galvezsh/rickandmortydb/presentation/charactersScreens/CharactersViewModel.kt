package com.galvezsh.rickandmortydb.presentation.charactersScreens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.galvezsh.rickandmortydb.domain.useCases.GetCharactersFlowUseCase
import com.galvezsh.rickandmortydb.domain.useCases.GetCountOfCharactersFlowUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.collections.component1
import kotlin.collections.component2
import kotlin.collections.component3

@HiltViewModel
class CharactersViewModel @Inject constructor(
    private val getAllCharactersFlow: GetCharactersFlowUseCase,
    private val getCountOfCharactersFlow: GetCountOfCharactersFlowUseCase
): ViewModel() {

    private val _from = MutableStateFlow<Int>( 0 )
    val from: StateFlow<Int> = _from

    private val _to = MutableStateFlow<Int>( 0 )
    val to: StateFlow<Int> = _to

    private val _searchQuery = MutableStateFlow( "" )
    val searchQuery: StateFlow<String> = _searchQuery

    private val _gender = MutableStateFlow( "" )

    private val _status = MutableStateFlow( "" )

    private val _genderIndex = MutableStateFlow( 0 )
    val genderIndex: StateFlow<Int> = _genderIndex

    private val _statusIndex = MutableStateFlow( 0 )
    val statusIndex: StateFlow<Int> = _statusIndex

    // With the variables searchQuery, gender and status, i create a Flow to be able to filter
    // the characters of the PagingData, all to be able to maintain the same Flow instance and
    // for it to work, otherwise it will not work, since in order to make the request to
    // 'flatMapLatest', we need the same instance whose internal values can change.
    private val filters = combine( _searchQuery, _gender, _status ) { (name, gender, status) ->
        Triple( name, gender, status )
    }

    // 'flatMapLatest' is a very interesting property since it allows, like a StateFlow, to observe
    // the changes that occur in its internal value when it is modified, and when that happens,
    // execute a code. In this case, I observe the 3 internal values and when one changes,
    // I execute the 'UseCase' again, obtaining the data again WITHOUT THE ORIGINAL 'FLOW' INSTANCE
    // CHANGING, since if this is done manually without Flow, the list will not be updated since
    // once the 'collectAsLazyPagingItems' is executed on the screen, it will observe the
    // instantiated Flow forever, since it is observing that same pending Flow for when the user
    // goes down the list and more data is needed because there is no more. That is why we need to
    // modify the original Flow instead of instantiating a new one manually.
    val characters = filters.flatMapLatest { (name, gender, status) ->
        getAllCharactersFlow( name, gender, status ).cachedIn( viewModelScope )
    }.flowOn( Dispatchers.Default )

    init {
        viewModelScope.launch {
            getCountOfCharactersFlow().collect { _to.value = it }
        }
    }

    fun onFromChanged( from: Int ) { _from.value = from }

    fun onSearchFieldChanged( query: String ) {
        _searchQuery.value = query
    }

    fun onGenderFilterChanged( newGender: String, newIndex: Int ) {
        _gender.value = newGender
        _genderIndex.value = newIndex
    }

    fun onStatusFilterChanged( newStatus: String, newIndex: Int ) {
        _status.value = newStatus
        _statusIndex.value = newIndex
    }
}