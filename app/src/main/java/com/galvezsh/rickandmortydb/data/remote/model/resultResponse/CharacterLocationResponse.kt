package com.galvezsh.rickandmortydb.data.remote.model.resultResponse

import com.google.gson.annotations.SerializedName

data class CharacterLocationResponse(
    @SerializedName("name") val name: String,
    @SerializedName("url") val url: String
): ResultResponse