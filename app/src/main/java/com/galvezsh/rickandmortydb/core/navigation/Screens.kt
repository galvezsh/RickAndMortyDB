package com.galvezsh.rickandmortydb.core.navigation

import androidx.compose.ui.graphics.vector.ImageVector
import kotlinx.serialization.Serializable

// In this file are describes the serialized objects that represent each screen on the app

@Serializable
object CharactersScreenSerial

@Serializable
data class DetailCharacterScreenSerial( val characterId: Int )

@Serializable
object LocationsScreenSerial

@Serializable
object EpisodesScreenSerial

@Serializable
object SettingsScreenSerial

// And this describes the route for the list of buttons for the BottomNavigationBar
data class NavigationBottomRoute<T : Any>( val icon: ImageVector, val name: String, val route: T )