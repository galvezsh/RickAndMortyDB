package com.galvezsh.rickandmortydb.domain.model

data class EpisodeModel(
    val id: Int,
    val name: String,
    val episode: String,
    val characters: List<String>
) {
    companion object {
        /**
         * Return a empty EpisodeModel
         */
        fun empty() = EpisodeModel( -1, "", "", emptyList<String>() )
    }
}