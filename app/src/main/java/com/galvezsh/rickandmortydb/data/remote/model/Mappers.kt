package com.galvezsh.rickandmortydb.data.remote.model

import com.galvezsh.rickandmortydb.data.remote.model.resultResponse.CharacterLocationResponse
import com.galvezsh.rickandmortydb.data.remote.model.resultResponse.CharacterResponse
import com.galvezsh.rickandmortydb.data.remote.model.resultResponse.EpisodeResponse
import com.galvezsh.rickandmortydb.data.remote.model.resultResponse.LocationResponse
import com.galvezsh.rickandmortydb.domain.model.CharacterLocationModel
import com.galvezsh.rickandmortydb.domain.model.CharacterModel
import com.galvezsh.rickandmortydb.domain.model.EpisodeModel
import com.galvezsh.rickandmortydb.domain.model.LocationModel

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

fun LocationResponse.toDomain(): LocationModel {
    return LocationModel(
        id = this.id,
        name = this.name,
        type = this.type,
        dimension = this.dimension,
        residents = this.residents
    )
}