package com.galvezsh.rickandmortydb.domain.useCases

import androidx.paging.PagingData
import com.galvezsh.rickandmortydb.data.repository.RickAndMortyRepository
import com.galvezsh.rickandmortydb.domain.model.EpisodeModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetEpisodesFlowUseCase @Inject constructor( private val repository: RickAndMortyRepository ) {
    operator fun invoke( name: String, season: String ): Flow<PagingData<EpisodeModel>> {
        return repository.getEpisodesFlow( name, season )
    }
}