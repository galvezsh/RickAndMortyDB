package com.galvezsh.rickandmortydb.data.remote.model.resultResponse

import com.google.gson.annotations.SerializedName

data class LocationResponse(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("type") val type: String,
    @SerializedName("dimension") val dimension: String,
    @SerializedName("residents") val residents: List<String>
): ResultResponse