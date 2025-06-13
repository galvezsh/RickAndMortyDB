package com.galvezsh.rickandmortydb.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.galvezsh.rickandmortydb.data.pagingSource.CharacterPagingSource
import com.galvezsh.rickandmortydb.domain.model.CharacterModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import javax.inject.Inject
import javax.inject.Singleton

// In this class are defined all the methods to request data Retrofit, Room, DataStore...)
@Singleton
class MainRepository @Inject constructor( private val api: RetrofitApiService ) {

    // This is a flow that contains the value of the total of characters that the api returns, to
    // display on the characters screen
    private val _totalCountFlow = MutableSharedFlow<Int>( replay = 1 )
    val totalCountFlow: SharedFlow<Int> = _totalCountFlow

    companion object {
        const val MAX_ITEMS = 20
        const val PREFETCH_ITEMS = 5
    }

    /**
     * This function is the responsible to request all the characters based on the parameters received
     *
     * @param name The name of the character
     * @param gender The gender of the character
     * @param status The status of the character
     *
     * @return Return a flow of 'PagingData' of 'CharacterModel'
     */
    fun getAllCharactersFlow( name: String, gender: String, status: String ): Flow<PagingData<CharacterModel>> {
        return Pager(
            config = PagingConfig(
                pageSize = MAX_ITEMS,
                prefetchDistance = PREFETCH_ITEMS
            ),
            pagingSourceFactory = { CharacterPagingSource( api, name, gender, status ) { totalCharacters ->
                _totalCountFlow.tryEmit( totalCharacters )
            } }
        ).flow
    }
//    /**
//     * This function is the responsible to request only one character from the api based on his id
//     *
//     * @param id The id of the character
//     *
//     * @return Return a nullable CharacterModel because, is possible that the id of the character wasn't valid
//     */
//    suspend fun getCharacterById( id: Int ): CharacterModel? {
//        return try {
//            val response = api.getCharacterById( id )
//
//            if ( response.isSuccessful )
//                return response.body()!!
//            else
//                null
//            // Something went wrong
//
//            // No internet
//        } catch ( ex: IOException ) {
//            null
//        }
//    }

}