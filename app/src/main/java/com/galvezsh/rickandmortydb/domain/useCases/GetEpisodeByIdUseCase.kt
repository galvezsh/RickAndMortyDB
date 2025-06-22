package com.galvezsh.rickandmortydb.domain.useCases

import com.galvezsh.rickandmortydb.data.MainRepository
import com.galvezsh.rickandmortydb.domain.model.EpisodeModel
import javax.inject.Inject

class GetEpisodeByIdUseCase @Inject constructor( private val repository: MainRepository ) {
    suspend operator fun invoke( episodeId: Int ): EpisodeModel? {
        return repository.getEpisodeById( episodeId )
    }
}