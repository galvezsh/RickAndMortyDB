package com.galvezsh.rickandmortydb.data.remote.model.resultResponse

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
): ResultResponse