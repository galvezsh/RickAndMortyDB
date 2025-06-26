package com.galvezsh.rickandmortydb.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.galvezsh.rickandmortydb.data.remote.api.RetrofitApiService
import com.galvezsh.rickandmortydb.data.pagingSource.CharacterPagingSource
import com.galvezsh.rickandmortydb.data.pagingSource.EpisodePagingSource
import com.galvezsh.rickandmortydb.data.pagingSource.LocationPagingSource
import com.galvezsh.rickandmortydb.domain.model.CharacterModel
import com.galvezsh.rickandmortydb.domain.model.EpisodeModel
import com.galvezsh.rickandmortydb.domain.model.LocationModel
import com.galvezsh.rickandmortydb.domain.repository.RickAndMortyRepositoryInterface
import com.galvezsh.rickandmortydb.data.remote.model.toDomain
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
class RickAndMortyRepository @Inject constructor( private val api: RetrofitApiService ): RickAndMortyRepositoryInterface {

    // This is a flow that contains the value of the total of characters that the api returns, to
    // display on the characters screen
    private val _totalCharactersFlow = MutableSharedFlow<Int>(replay = 1)
    val totalCharactersFlow: SharedFlow<Int> = _totalCharactersFlow

    private val _totalEpisodesFlow = MutableSharedFlow<Int>(replay = 1)
    val totalEpisodesFlow: SharedFlow<Int> = _totalEpisodesFlow

    private val _totalLocationsFlow = MutableSharedFlow<Int>(replay = 1)
    val totalLocationsFlow: SharedFlow<Int> = _totalLocationsFlow

    companion object {
        const val MAX_ITEMS = 20
        const val PREFETCH_ITEMS = 5
    }
    
    override fun getCharactersFlow( name: String, gender: String, status: String ): Flow<PagingData<CharacterModel>> {
        return Pager(
            config = PagingConfig(pageSize = MAX_ITEMS, prefetchDistance = PREFETCH_ITEMS),
            pagingSourceFactory = {
                CharacterPagingSource(api, name, gender, status) { totalCharacters ->
                    _totalCharactersFlow.tryEmit(totalCharacters)
                }
            }
        ).flow.flowOn( Dispatchers.IO )
    }

    override suspend fun getCharacterById( id: Int ): CharacterModel? {
        return try {
            val response = api.getCharacterById( id )

            return if ( response.isSuccessful )
                response.body()!!.toDomain()
            else
                CharacterModel.Companion.empty()
            // Something went wrong

            // No internet
        } catch ( ex: IOException) {
            null
        }
    }

    override fun getEpisodesFlow( name: String, season: String ): Flow<PagingData<EpisodeModel>> {
        return Pager(
            config = PagingConfig(pageSize = MAX_ITEMS, prefetchDistance = PREFETCH_ITEMS),
            pagingSourceFactory = {
                EpisodePagingSource(api, name, season) { totalEpisodes ->
                    _totalEpisodesFlow.tryEmit(totalEpisodes)
                }
            }
        ).flow.flowOn( Dispatchers.IO )
    }

    override suspend fun getEpisodeById( id: Int ): EpisodeModel? {
        return try {
            val response = api.getEpisodeById( id )

            return if ( response.isSuccessful )
                response.body()!!.toDomain()
            else
                EpisodeModel.Companion.empty()
            // Something went wrong

            // No internet
        } catch ( ex: IOException) {
            null
        }
    }

    override fun getLocationsFlow( name: String, type: String ): Flow<PagingData<LocationModel>> {
        return Pager(
            config = PagingConfig(pageSize = MAX_ITEMS, prefetchDistance = PREFETCH_ITEMS),
            pagingSourceFactory = {
                LocationPagingSource(api, name, type) { totalLocations ->
                    _totalLocationsFlow.tryEmit(totalLocations)
                }
            }
        ).flow.flowOn( Dispatchers.IO )
    }

    override suspend fun getLocationById( id: Int ): LocationModel? {
        return try {
            val response = api.getLocationById( id )

            return if ( response.isSuccessful )
                response.body()!!.toDomain()
            else
                LocationModel.Companion.empty()
            // Something went wrong

            // No internet
        } catch ( ex: IOException) {
            null
        }
    }
}