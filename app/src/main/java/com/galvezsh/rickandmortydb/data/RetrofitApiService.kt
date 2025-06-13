package com.galvezsh.rickandmortydb.data

import com.galvezsh.rickandmortydb.data.retrofitResponse.CharacterResponse
import com.galvezsh.rickandmortydb.data.retrofitResponse.ResponseWrapper
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
    ): Response<ResponseWrapper>

    @GET("/api/character/{id}")
    suspend fun getCharacterById(
        @Path("id") id: Int
    ): Response<CharacterResponse>
}