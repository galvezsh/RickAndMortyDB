package com.galvezsh.rickandmortydb.presentation.settingsScreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import com.galvezsh.rickandmortydb.R
import com.galvezsh.rickandmortydb.presentation.shared.AppBottomNavigationBar
import com.galvezsh.rickandmortydb.presentation.shared.AppTopInfoBar

@Composable
fun SettingsScreen(
    navController: NavHostController,
    currentDestination: NavDestination?,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    Scaffold( topBar = {
        AppTopInfoBar( text = stringResource( R.string.settings ).uppercase() )
    }, bottomBar = {
        AppBottomNavigationBar( navController, currentDestination )
    } ) { innerPadding ->

        Box( modifier = Modifier.fillMaxSize().padding( innerPadding ) ) {
            Text(
                text = stringResource( R.string.not_implemented ),
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.align( Alignment.Center )
            )
        }
    }
}