package com.galvezsh.rickandmortydb.data.retrofitResponse

import com.galvezsh.rickandmortydb.domain.model.CharacterModel
import com.google.gson.annotations.SerializedName

data class CharacterResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("status") val status: String,
    @SerializedName("species") val species: String?,
    @SerializedName("type") val type: String?,
    @SerializedName("gender") val gender: String?,
    @SerializedName("origin") val origin: CharacterLocationResponse,
    @SerializedName("location") val location: CharacterLocationResponse,
    @SerializedName("image") val image: String?,
    @SerializedName("episode") val episode: List<String>
) {
    fun toMap(): CharacterModel {
        return CharacterModel(
            id = id,
            name = name,
            isAlive = status,
            species = species ?: "",
            type = type ?: "",
            gender = gender ?: "",
            image = image ?: "",
            origin = origin.toMap(),
            location = location.toMap(),
            episodes = episode
        )
    }
}