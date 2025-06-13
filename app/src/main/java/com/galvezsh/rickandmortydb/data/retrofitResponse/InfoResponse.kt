package com.galvezsh.rickandmortydb.data.retrofitResponse

import com.google.gson.annotations.SerializedName

data class InfoResponse(
    @SerializedName("count") val count: Int,
    @SerializedName("pages") val pages: Short,
    @SerializedName("next") val next: String?,
    @SerializedName("prev") val prev: String?
)