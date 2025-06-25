package com.galvezsh.rickandmortydb.presentation.settingsScreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.galvezsh.rickandmortydb.R
import com.galvezsh.rickandmortydb.presentation.ShowHeader

@Composable
fun SettingsScreen( viewModel: SettingsViewModel = hiltViewModel() ) {

    ShowHeader( text = stringResource( R.string.settings ).uppercase() ) {
        Box( modifier = Modifier.fillMaxSize() ) {
            Text(
                text = stringResource( R.string.not_implemented ),
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.surface,
                modifier = Modifier.align( Alignment.Center )
            )
        }
    }
}