package com.galvezsh.rickandmortydb.domain.useCases

import androidx.paging.PagingData
import com.galvezsh.rickandmortydb.data.repository.RickAndMortyRepository
import com.galvezsh.rickandmortydb.domain.model.LocationModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLocationsFlowUseCase @Inject constructor( private val repository: RickAndMortyRepository ) {
    operator fun invoke( name: String, type: String ): Flow<PagingData<LocationModel>> {
        return repository.getLocationsFlow( name, type )
    }
}