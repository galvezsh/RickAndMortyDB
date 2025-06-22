package com.galvezsh.rickandmortydb.domain.useCases

import androidx.paging.PagingData
import com.galvezsh.rickandmortydb.data.MainRepository
import com.galvezsh.rickandmortydb.domain.model.CharacterModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * This class represents a repository method, specifically, where all the characters in the repository
 * are retrieved. More specifically, it retrieves a Flow of PagingData from the CharacterModel.
 * Since a single calling function is declared, it can be called directly with the variable name,
 * along with parentheses at the end, like -> 'getAllCharactersFlow()'. Remember use the lowercase
 * because is the variable itself
 */
class GetCharactersFlowUseCase @Inject constructor(private val repository: MainRepository ) {

    operator fun invoke( name: String, gender: String, status: String ): Flow<PagingData<CharacterModel>> {
        return repository.getCharactersFlow( name, gender, status )
    }
}