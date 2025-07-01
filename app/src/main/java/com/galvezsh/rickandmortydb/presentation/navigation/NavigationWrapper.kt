package com.galvezsh.rickandmortydb.presentation.navigation

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.galvezsh.rickandmortydb.presentation.charactersScreens.CharactersScreen
import com.galvezsh.rickandmortydb.presentation.charactersScreens.DetailCharacterScreen
import com.galvezsh.rickandmortydb.presentation.episodesScreens.DetailEpisodeScreen
import com.galvezsh.rickandmortydb.presentation.episodesScreens.EpisodesScreen
import com.galvezsh.rickandmortydb.presentation.locationsScreens.DetailLocationScreen
import com.galvezsh.rickandmortydb.presentation.locationsScreens.LocationsScreen

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
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
        popEnterTransition = { EnterTransition.None },
        popExitTransition = { ExitTransition.None }
    ) {
        composable<CharactersScreenSerial> {
            CharactersScreen(
                navController = navController,
                currentDestination = currentDestination,
                navigateToDetailCharacter = { id ->
                navController.navigate(DetailCharacterScreenSerial(id))
            })
        }

        composable<DetailCharacterScreenSerial>(
            enterTransition = { slideInHorizontally(initialOffsetX = { it }) },
            exitTransition = { slideOutHorizontally(targetOffsetX = { -it }) },
            popEnterTransition = { slideInHorizontally(initialOffsetX = { -it }) },
            popExitTransition = { slideOutHorizontally(targetOffsetX = { it }) }
        ) {
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

        composable<DetailEpisodeScreenSerial>(
            enterTransition = { slideInHorizontally(initialOffsetX = { it }) },
            exitTransition = { slideOutHorizontally(targetOffsetX = { -it }) },
            popEnterTransition = { slideInHorizontally(initialOffsetX = { -it }) },
            popExitTransition = { slideOutHorizontally(targetOffsetX = { it }) }
        ) {
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

        composable<DetailLocationScreenSerial>(
            enterTransition = { slideInHorizontally(initialOffsetX = { it }) },
            exitTransition = { slideOutHorizontally(targetOffsetX = { -it }) },
            popEnterTransition = { slideInHorizontally(initialOffsetX = { -it }) },
            popExitTransition = { slideOutHorizontally(targetOffsetX = { it }) }
        ) {
            DetailLocationScreen(
                navigateToDetailCharacter = { id ->
                    navController.navigate(DetailCharacterScreenSerial(id))
                }
            )
        }
    }
}
