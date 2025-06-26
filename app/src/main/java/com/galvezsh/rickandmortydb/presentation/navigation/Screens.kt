package com.galvezsh.rickandmortydb.presentation.navigation

import androidx.compose.ui.graphics.vector.ImageVector
import kotlinx.serialization.Serializable

// In this file are describes the serialized objects that represent each screen on the app

@Serializable
object CharactersScreenSerial

@Serializable
data class DetailCharacterScreenSerial( val characterId: Int )

@Serializable
object EpisodesScreenSerial

@Serializable
data class DetailEpisodeScreenSerial( val episodeId: Int )

@Serializable
object LocationsScreenSerial

@Serializable
data class DetailLocationScreenSerial( val locationId: Int )

@Serializable
object SettingsScreenSerial

// And this describes the route for the list of buttons for the BottomNavigationBar
data class NavigationBottomRoute<T : Any>( val icon: ImageVector, val name: String, val route: T )