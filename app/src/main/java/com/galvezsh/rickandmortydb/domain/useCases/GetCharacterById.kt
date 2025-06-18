package com.galvezsh.rickandmortydb.domain.useCases

import com.galvezsh.rickandmortydb.data.MainRepository
import com.galvezsh.rickandmortydb.domain.model.CharacterModel
import javax.inject.Inject

class GetCharacterById @Inject constructor( private val repository: MainRepository ) {
    suspend operator fun invoke( characterId: Int ): CharacterModel? {
        return repository.getCharacterById( characterId )
    }
}