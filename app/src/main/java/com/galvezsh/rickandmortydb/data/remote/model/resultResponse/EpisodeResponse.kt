package com.galvezsh.rickandmortydb.data.remote.model.resultResponse

import com.google.gson.annotations.SerializedName

data class EpisodeResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("episode") val episode: String,
    @SerializedName("characters") val characters: List<String>
): ResultResponse