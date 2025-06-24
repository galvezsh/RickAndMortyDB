package com.galvezsh.rickandmortydb.domain.useCases

import com.galvezsh.rickandmortydb.data.repository.RickAndMortyRepository
import com.galvezsh.rickandmortydb.domain.model.CharacterModel
import javax.inject.Inject

class GetCharacterByIdUseCase @Inject constructor(private val repository: RickAndMortyRepository ) {
    suspend operator fun invoke( characterId: Int ): CharacterModel? {
        return repository.getCharacterById( characterId )
    }
}