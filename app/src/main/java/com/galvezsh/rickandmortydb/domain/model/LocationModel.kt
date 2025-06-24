package com.galvezsh.rickandmortydb.domain.model

data class LocationModel(
    val id: Int,
    val name: String,
    val type: String,
    val dimension: String,
    val residents: List<String>
) {
    companion object {
        /**
         * Return a empty LocationModel
         */
        fun empty() = LocationModel( -1, "", "", "", emptyList<String>() )
    }
}