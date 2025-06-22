package com.galvezsh.rickandmortydb.mappers

import com.galvezsh.rickandmortydb.data.retrofitResponse.characterResponse.CharacterLocationResponse
import com.galvezsh.rickandmortydb.data.retrofitResponse.characterResponse.CharacterResponse
import com.galvezsh.rickandmortydb.data.retrofitResponse.episodeResponse.EpisodeResponse
import com.galvezsh.rickandmortydb.domain.model.CharacterLocationModel
import com.galvezsh.rickandmortydb.domain.model.CharacterModel
import com.galvezsh.rickandmortydb.domain.model.EpisodeModel

fun CharacterResponse.toDomain(): CharacterModel {
    return CharacterModel(
        id = this.id,
        name = this.name,
        isAlive = this.status,
        species = this.species ?: "",
        type = this.type ?: "",
        gender = this.gender ?: "",
        image = this.image ?: "",
        origin = this.origin.toDomain(),
        location = this.location.toDomain(),
        episodes = this.episode
    )
}

fun CharacterLocationResponse.toDomain(): CharacterLocationModel {
    return CharacterLocationModel(
        name = this.name,
        url = this.url
    )
}

fun EpisodeResponse.toDomain(): EpisodeModel {
    return EpisodeModel(
        id = this.id,
        name = this.name,
        episode = this.episode,
        characters = this.characters
    )
}