package com.galvezsh.rickandmortydb.core.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.FormatListNumbered
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.Place
import androidx.compose.material.icons.rounded.Settings
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

val navigationBottomRoutes = listOf(
    NavigationBottomRoute( Icons.Rounded.Person , "Personajes", CharactersScreen ),
    NavigationBottomRoute( Icons.Rounded.FormatListNumbered , "Episodios", EpisodesScreen ),
    NavigationBottomRoute( Icons.Rounded.Place , "Lugares", LocationsScreen ),
    NavigationBottomRoute( Icons.Rounded.Settings , "Ajustes", SettingsScreen )
)