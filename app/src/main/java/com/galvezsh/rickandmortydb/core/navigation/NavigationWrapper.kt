package com.galvezsh.rickandmortydb.core.navigation

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.getValue
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.galvezsh.rickandmortydb.presentation.charactersScreen.CharactersScreen
import com.galvezsh.rickandmortydb.presentation.episodesScreen.EpisodesScreen
import com.galvezsh.rickandmortydb.presentation.locationsScreen.LocationsScreen
import com.galvezsh.rickandmortydb.presentation.settingsScreen.SettingsScreen

@Composable
fun NavigationWrapper() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = MaterialTheme.colorScheme.background,
                modifier = Modifier.navigationBarsPadding()
            ) {
                navigationBottomRoutes.forEach { item ->
                    NavigationBarItem(
                        icon = { Icon( item.icon, contentDescription = item.name ) },
                        label = { Text( item.name ) },
                        selected = currentDestination?.hierarchy?.any { it.hasRoute( item.route::class ) } == true,
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Color.Blue,
                            unselectedIconColor = MaterialTheme.colorScheme.surface,
                            selectedTextColor = Color.Blue,
                            unselectedTextColor = MaterialTheme.colorScheme.surface,
                            indicatorColor = Color.Transparent
                        ),
                        onClick = {
                            navController.navigate( item.route ) {
                                popUpTo( navController.graph.findStartDestination().id ) { saveState = true }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = CharactersScreen,
            modifier = Modifier.padding( innerPadding )
        ) {
            composable<CharactersScreen> { CharactersScreen() }
            composable<EpisodesScreen> { EpisodesScreen() }
            composable<LocationsScreen> { LocationsScreen() }
            composable<SettingsScreen> { SettingsScreen() }
        }
    }

}