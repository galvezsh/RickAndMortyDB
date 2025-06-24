package com.galvezsh.rickandmortydb.data.remote.model

import com.galvezsh.rickandmortydb.data.remote.model.infoResponse.InfoResponse
import com.galvezsh.rickandmortydb.data.remote.model.resultResponse.ResultResponse
import com.google.gson.annotations.SerializedName

data class ResponseWrapper<T: ResultResponse>(
    @SerializedName("info") val info: InfoResponse,
    @SerializedName("results") val results: List<T>
)