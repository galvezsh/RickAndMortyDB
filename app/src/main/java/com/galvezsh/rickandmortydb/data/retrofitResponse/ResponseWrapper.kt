package com.galvezsh.rickandmortydb.data.retrofitResponse

import com.google.gson.annotations.SerializedName

// This is the data class tha Retrofit return when data is request from the api
data class ResponseWrapper(
    @SerializedName("info") val info: InfoResponse,
    @SerializedName("results") val results: List<CharacterResponse>
//    @SerializedName("error") val error: String // Check for this kind of error when wrong data is request
)