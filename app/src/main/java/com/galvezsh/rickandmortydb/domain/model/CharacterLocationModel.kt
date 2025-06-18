package com.galvezsh.rickandmortydb.domain.model

data class CharacterLocationModel(
    val name: String,
    val url: String
) {
    companion object {
        /**
         * Return a empty CharacterLocationModel
         */
        fun empty() = CharacterLocationModel( "", "" )
    }
}