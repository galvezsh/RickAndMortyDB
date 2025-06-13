package com.galvezsh.rickandmortydb.domain.model

data class CharacterModel(
    val id: Int,
    val name: String,
    val isAlive: String,
    val species: String,
    val type: String,
    val gender: String,
    val image: String,
    val origin: CharacterLocationModel,
    val location: CharacterLocationModel,
    val episodes: List<String>
)