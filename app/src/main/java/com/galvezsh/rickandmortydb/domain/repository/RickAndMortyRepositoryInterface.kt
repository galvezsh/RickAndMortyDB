package com.galvezsh.rickandmortydb.domain.repository

import androidx.paging.PagingData
import com.galvezsh.rickandmortydb.domain.model.CharacterModel
import com.galvezsh.rickandmortydb.domain.model.EpisodeModel
import com.galvezsh.rickandmortydb.domain.model.LocationModel
import kotlinx.coroutines.flow.Flow

interface RickAndMortyRepositoryInterface {
    /**
     * This function is the responsible to request all the characters based on the parameters received
     *
     * @param name The name of the character
     * @param gender The gender of the character
     * @param status The status of the character
     *
     * @return Return a flow of 'PagingData' of 'CharacterModel'
     */
    fun getCharactersFlow( name: String, gender: String, status: String ): Flow<PagingData<CharacterModel>>

    /**
     * This function is the responsible to request only one character from the api based on his id
     *
     * @param id The id of the character
     *
     * @return Return a nullable CharacterModel because, is possible that the id of the character wasn't valid
     */
    suspend fun getCharacterById( id: Int ): CharacterModel?

    /**
     * This function is the responsible to request all the episodes based on the parameters received
     *
     * @param name The name of the episode
     * @param season The season of the TV Show
     *
     * @return Return a flow of 'PagingData' of 'EpisodeModel'
     */
    fun getEpisodesFlow( name: String, season: String ): Flow<PagingData<EpisodeModel>>

    /**
     * This function is the responsible to request only one episode from the api based on his id
     *
     * @param id The id of the episode
     *
     * @return Return a nullable EpisodeModel because, is possible that the id of the episode wasn't valid
     */
    suspend fun getEpisodeById( id: Int ): EpisodeModel?

    /**
     * This function is the responsible to request all the locations based on the parameters received
     *
     * @param name The name of the planet/location
     * @param type The type of the planet/location
     *
     * @return Return a flow of 'PagingData' of 'LocationModel'
     */
    fun getLocationsFlow( name: String, type: String ): Flow<PagingData<LocationModel>>

    /**
     * This function is the responsible to request only one location from the api based on his id
     *
     * @param id The id of the location
     *
     * @return Return a nullable LocationModel because, is possible that the id of the location wasn't valid
     */
    suspend fun getLocationById( id: Int ): LocationModel?
}