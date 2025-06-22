package com.galvezsh.rickandmortydb.data.retrofitResponse.characterResponse

import com.google.gson.annotations.SerializedName

data class CharacterLocationResponse(
    @SerializedName("name") val name: String,
    @SerializedName("url") val url: String
)