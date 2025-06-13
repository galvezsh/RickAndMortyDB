package com.galvezsh.rickandmortydb.data.retrofitResponse

import com.galvezsh.rickandmortydb.domain.model.CharacterLocationModel
import com.google.gson.annotations.SerializedName

data class CharacterLocationResponse(
    @SerializedName("name") val name: String,
    @SerializedName("url") val url: String
) {

    fun toMap(): CharacterLocationModel {
        return CharacterLocationModel(
            name = name,
            url = url
        )
    }
}