package com.galvezsh.rickandmortydb.core.navigation

import androidx.compose.ui.graphics.vector.ImageVector
import kotlinx.serialization.Serializable

@Serializable
object CharactersScreen

@Serializable
object LocationsScreen

@Serializable
object EpisodesScreen

@Serializable
object SettingsScreen

data class NavigationBottomRoute<T : Any>( val icon: ImageVector, val name: String, val route: T )