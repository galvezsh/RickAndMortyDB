package com.galvezsh.rickandmortydb.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.galvezsh.rickandmortydb.data.pagingSource.CharacterPagingSource
import com.galvezsh.rickandmortydb.data.pagingSource.EpisodePagingSource
import com.galvezsh.rickandmortydb.domain.model.CharacterModel
import com.galvezsh.rickandmortydb.domain.model.EpisodeModel
import com.galvezsh.rickandmortydb.mappers.toDomain
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.flowOn
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

// In this class are defined all the methods to request data Retrofit, Room, DataStore...)
@Singleton
class MainRepository @Inject constructor( private val api: RetrofitApiService ) {

    // This is a flow that contains the value of the total of characters that the api returns, to
    // display on the characters screen
    private val _totalCharactersFlow = MutableSharedFlow<Int>( replay = 1 )
    val totalCharactersFlow: SharedFlow<Int> = _totalCharactersFlow

    private val _totalEpisodesFlow = MutableSharedFlow<Int>( replay = 1 )
    val totalEpisodesFlow: SharedFlow<Int> = _totalEpisodesFlow

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
    fun getCharactersFlow( name: String, gender: String, status: String ): Flow<PagingData<CharacterModel>> {
        return Pager(
            config = PagingConfig( pageSize = MAX_ITEMS, prefetchDistance = PREFETCH_ITEMS ),
            pagingSourceFactory = { CharacterPagingSource( api, name, gender, status ) { totalCharacters ->
                _totalCharactersFlow.tryEmit( totalCharacters )
            } }
        ).flow.flowOn( Dispatchers.IO )
    }
    /**
     * This function is the responsible to request only one character from the api based on his id
     *
     * @param id The id of the character
     *
     * @return Return a nullable CharacterModel because, is possible that the id of the character wasn't valid
     */
    suspend fun getCharacterById( id: Int ): CharacterModel? {
        return try {
            val response = api.getCharacterById( id )

            return if ( response.isSuccessful )
                response.body()!!.toDomain()
            else
                CharacterModel.empty()
            // Something went wrong

            // No internet
        } catch ( ex: IOException ) {
            null
        }
    }

    /**
     * This function is the responsible to request all the episodes based on the parameters received
     *
     * @param name The name of the episode
     * @param season The season of the TV Show
     *
     * @return Return a flow of 'PagingData' of 'EpisodeModel'
     */
    fun getEpisodesFlow( name: String, season: String ): Flow<PagingData<EpisodeModel>> {
        return Pager(
            config = PagingConfig( pageSize = MAX_ITEMS, prefetchDistance = PREFETCH_ITEMS ),
            pagingSourceFactory = { EpisodePagingSource( api, name, season ) { totalEpisodes ->
                _totalEpisodesFlow.tryEmit( totalEpisodes )
            } }
        ).flow.flowOn( Dispatchers.IO )
    }

    /**
     * This function is the responsible to request only one episode from the api based on his id
     *
     * @param id The id of the episode
     *
     * @return Return a nullable EpisodeModel because, is possible that the id of the episode wasn't valid
     */
    suspend fun getEpisodeById( id: Int ): EpisodeModel? {
        return try {
            val response = api.getEpisodeById( id )

            return if ( response.isSuccessful )
                response.body()!!.toDomain()
            else
                EpisodeModel.empty()
            // Something went wrong

            // No internet
        } catch ( ex: IOException ) {
            null
        }
    }
}