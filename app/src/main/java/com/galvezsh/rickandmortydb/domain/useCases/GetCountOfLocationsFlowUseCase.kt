package com.galvezsh.rickandmortydb.domain.useCases

import com.galvezsh.rickandmortydb.data.repository.RickAndMortyRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCountOfLocationsFlowUseCase @Inject constructor( private val repository: RickAndMortyRepository ) {
    operator fun invoke(): Flow<Int> {
        return repository.totalLocationsFlow
    }
}