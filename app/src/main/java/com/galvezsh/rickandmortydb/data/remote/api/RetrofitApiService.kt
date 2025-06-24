package com.galvezsh.rickandmortydb.data.remote.api

import com.galvezsh.rickandmortydb.data.remote.model.ResponseWrapper
import com.galvezsh.rickandmortydb.data.remote.model.resultResponse.CharacterResponse
import com.galvezsh.rickandmortydb.data.remote.model.resultResponse.EpisodeResponse
import com.galvezsh.rickandmortydb.data.remote.model.resultResponse.LocationResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

// This is the interface that contains the methods to request data from the api
interface RetrofitApiService {

    @GET("/api/character/")
    suspend fun getCharacters(
        @Query("page") page: Int,
        @Query("name") name: String,
        @Query("gender") gender: String,
        @Query("status") status: String?
    ): Response<ResponseWrapper<CharacterResponse>>

    @GET("/api/character/{id}")
    suspend fun getCharacterById(
        @Path("id") id: Int
    ): Response<CharacterResponse>

    @GET("/api/episode/")
    suspend fun getEpisodes(
        @Query("page") page: Int,
        @Query("name") name: String,
        @Query("episode") episode: String
    ): Response<ResponseWrapper<EpisodeResponse>>

    @GET("/api/episode/{id}")
    suspend fun getEpisodeById(
        @Path("id") id: Int
    ): Response<EpisodeResponse>

    @GET("/api/location/")
    suspend fun getLocations(
        @Query("page") page: Int,
        @Query("name") name: String,
        @Query("type") type: String
    ): Response<ResponseWrapper<LocationResponse>>

    @GET("/api/location/{id}")
    suspend fun getLocationById(
        @Path("id") id: Int
    ): Response<LocationResponse>
}