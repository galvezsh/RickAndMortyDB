package com.galvezsh.rickandmortydb.data.retrofitResponse.episodeResponse

import com.galvezsh.rickandmortydb.data.retrofitResponse.InfoResponse
import com.google.gson.annotations.SerializedName

data class EpisodeResponseWrapper(
    @SerializedName("info") val info: InfoResponse,
    @SerializedName("results") val results: List<EpisodeResponse>
)