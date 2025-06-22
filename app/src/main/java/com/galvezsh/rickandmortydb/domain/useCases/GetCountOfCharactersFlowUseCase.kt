package com.galvezsh.rickandmortydb.domain.useCases

import com.galvezsh.rickandmortydb.data.MainRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCountOfCharactersFlowUseCase @Inject constructor(private val repository: MainRepository ) {
    operator fun invoke(): Flow<Int> {
        return repository.totalCharactersFlow
    }
}