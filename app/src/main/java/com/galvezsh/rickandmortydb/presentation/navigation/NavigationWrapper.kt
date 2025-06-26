package com.galvezsh.rickandmortydb.presentation.navigation

import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.FormatListNumbered
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.Place
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.galvezsh.rickandmortydb.R
import com.galvezsh.rickandmortydb.presentation.charactersScreens.CharactersScreen
import com.galvezsh.rickandmortydb.presentation.charactersScreens.DetailCharacterScreen
import com.galvezsh.rickandmortydb.presentation.episodesScreens.DetailEpisodeScreen
import com.galvezsh.rickandmortydb.presentation.episodesScreens.EpisodesScreen
import com.galvezsh.rickandmortydb.presentation.locationsScreens.DetailLocationScreen
import com.galvezsh.rickandmortydb.presentation.locationsScreens.LocationsScreen
import com.galvezsh.rickandmortydb.presentation.settingsScreen.SettingsScreen

// Everything starts here, with the definition of the navigation.

/**
 * This composable is the responsible one to create the BottomNavigationBar for all the 4 Screens
 * and permit the navigation between each-one. After that, initialize the first screen, the
 * characters screen
 */
@Composable
fun NavigationWrapper() {

    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    NavHost(
        navController = navController,
        startDestination = CharactersScreenSerial, // <- here is the first screen to render
    ) {
        composable<CharactersScreenSerial> {
            CharactersScreen(
                navController = navController,
                currentDestination = currentDestination,
                navigateToDetailCharacter = { id ->
                navController.navigate(DetailCharacterScreenSerial(id))
            })
        }

        composable<DetailCharacterScreenSerial> {
            DetailCharacterScreen( // The screen receives the characterId in the ViewModel directly
                navigateToDetailLocation = { id ->
                    navController.navigate(DetailLocationScreenSerial(id))
                },
                navigateToDetailEpisode = { id ->
                    navController.navigate(DetailEpisodeScreenSerial(id))
                }
            )
        }

        composable<EpisodesScreenSerial> {
            EpisodesScreen(
                navController = navController,
                currentDestination = currentDestination,
                navigateToDetailEpisode = { id ->
                navController.navigate(DetailEpisodeScreenSerial(id))
            })
        }

        composable<DetailEpisodeScreenSerial> {
            DetailEpisodeScreen( // The screen receives the episodeId in the ViewModel directly
                navigateToDetailCharacter = { id ->
                    navController.navigate(DetailCharacterScreenSerial(id))
                }
            )
        }

        composable<LocationsScreenSerial> {
            LocationsScreen(
                navController = navController,
                currentDestination = currentDestination,
                navigateToDetailLocation = { id ->
                    navController.navigate(DetailLocationScreenSerial(id))
                }
            )
        }

        composable<DetailLocationScreenSerial> {
            DetailLocationScreen(
                navigateToDetailCharacter = { id ->
                    navController.navigate(DetailCharacterScreenSerial(id))
                }
            )
        }

        composable<SettingsScreenSerial> {
            SettingsScreen(
                navController = navController,
                currentDestination = currentDestination
            )
        }
    }
}

/**
 * This function build the bottom navigation bar with 4 buttons; characters, episodes, locations and settings,
 * where each button navigate to is own screen
 *
 * @param navController The navigation controller that allows the navigation between screens
 * @param currentDestination The current position when the app starts
 */
@Composable
fun BottomNavigationBar( navController: NavHostController, currentDestination: NavDestination?) {

    val borderColor = MaterialTheme.colorScheme.surface
    // This is the list of the routes for the physical buttons in the bar when i describes the icon for the button,
    // the bottom text of the button and the route that takes when the user press this button
    val navigationBottomRoutes = listOf(
        NavigationBottomRoute(Icons.Rounded.Person, stringResource(R.string.characters), CharactersScreenSerial),
        NavigationBottomRoute(Icons.Rounded.FormatListNumbered, stringResource(R.string.episodes), EpisodesScreenSerial),
        NavigationBottomRoute(Icons.Rounded.Place, stringResource(R.string.locations), LocationsScreenSerial),
        NavigationBottomRoute(Icons.Rounded.Settings, stringResource(R.string.settings), SettingsScreenSerial)
    )

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.background,
        modifier = Modifier.navigationBarsPadding().drawBehind {
            drawLine(
                color = borderColor,
                start = Offset(0f, 0f),
                end = Offset(size.width, 0f),
                strokeWidth = 5.dp.toPx()
            )
        }
    ) {
        // And this is where you go through the list to build each button
        navigationBottomRoutes.forEach { item ->
            NavigationBarItem(
                icon = { Icon(item.icon, contentDescription = item.name) },
                label = { Text(item.name) },
                selected = currentDestination?.hierarchy?.any { it.hasRoute(item.route::class) } == true,
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.surfaceVariant,
                    unselectedIconColor = MaterialTheme.colorScheme.surface,
                    selectedTextColor = MaterialTheme.colorScheme.surfaceVariant,
                    unselectedTextColor = MaterialTheme.colorScheme.surface,
                    indicatorColor = MaterialTheme.colorScheme.surfaceVariant.copy(0.2f)
                ),
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}
