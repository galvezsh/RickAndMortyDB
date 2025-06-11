package com.galvezsh.rickandmortydb.presentation.settingsScreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.galvezsh.rickandmortydb.R
import com.galvezsh.rickandmortydb.presentation.ShowSpacer

@Composable
fun SettingsScreen( viewModel: SettingsViewModel = hiltViewModel() ) {
    Box( modifier = Modifier.fillMaxSize().padding( horizontal = 24.dp ).padding( top = 14.dp ) ) {
        Column {
            SettingsHeader( modifier = Modifier.align( Alignment.CenterHorizontally ) )
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
}

@Composable
fun SettingsHeader( modifier: Modifier ) {
    Text(
        text = stringResource( R.string.settings ).uppercase(),
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
        modifier = modifier,
        color = MaterialTheme.colorScheme.surface
    )
    HorizontalDivider(
        thickness = 2.dp,
        color = MaterialTheme.colorScheme.surface,
        modifier = Modifier.padding( top = 14.dp )
    )
}