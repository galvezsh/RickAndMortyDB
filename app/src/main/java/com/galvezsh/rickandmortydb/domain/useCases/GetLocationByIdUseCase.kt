package com.galvezsh.rickandmortydb.domain.useCases

import com.galvezsh.rickandmortydb.data.repository.RickAndMortyRepository
import com.galvezsh.rickandmortydb.domain.model.EpisodeModel
import com.galvezsh.rickandmortydb.domain.model.LocationModel
import javax.inject.Inject

class GetLocationByIdUseCase @Inject constructor( private val repository: RickAndMortyRepository ) {
    suspend operator fun invoke( id: Int ): LocationModel? {
        return repository.getLocationById( id )
    }
}