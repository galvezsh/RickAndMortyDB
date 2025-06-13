package com.galvezsh.rickandmortydb.domain.useCases

import com.galvezsh.rickandmortydb.data.MainRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCountOfCharactersFlow @Inject constructor( private val repository: MainRepository ) {
    suspend operator fun invoke( onCountChanged: (Int) -> Unit ): Flow<Int> {
        repository.totalCountFlow.collect { totalCharacters ->
            onCountChanged( totalCharacters )
        }
    }
}