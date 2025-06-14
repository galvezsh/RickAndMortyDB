package com.galvezsh.rickandmortydb.presentation.settingsScreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import com.galvezsh.rickandmortydb.presentation.NotImplementedYet

@Composable
fun SettingsScreen( viewModel: SettingsViewModel = hiltViewModel() ) {
    ShowHeader( text = stringResource( R.string.settings ).uppercase() ) {
        NotImplementedYet()
    }
}

@Composable
private fun ShowHeader( text: String, content: @Composable () -> Unit ) {
    Column( modifier = Modifier.fillMaxSize() ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding( top = 14.dp ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = text,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.surface,
            )

            HorizontalDivider(
                thickness = 2.dp,
                color = MaterialTheme.colorScheme.surface,
                modifier = Modifier.padding( top = 14.dp )
            )
        }

        Box( modifier = Modifier.fillMaxSize() ) {
            content()
        }
    }
}