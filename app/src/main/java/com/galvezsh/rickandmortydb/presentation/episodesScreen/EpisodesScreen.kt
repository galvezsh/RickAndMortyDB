package com.galvezsh.rickandmortydb.presentation.episodesScreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.galvezsh.rickandmortydb.R
import com.galvezsh.rickandmortydb.presentation.ShowHeader
import com.galvezsh.rickandmortydb.presentation.ShowSearchField

@Composable
fun EpisodesScreen( viewModel: EpisodesViewModel = hiltViewModel() ) {
    Box( modifier = Modifier.fillMaxSize().padding( horizontal = 24.dp ).padding( top = 10.dp ) ) {
        Column {
            Header( viewModel )
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

    Scaffold {  }
}

@Composable
private fun Header( viewModel: EpisodesViewModel ) {
    val from by viewModel.from.collectAsState()
    val to by viewModel.to.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    var visibilitySF = remember { mutableStateOf( false ) }

    ShowHeader(
        text = stringResource( R.string.episodes ).uppercase(),
        from = from,
        to = to,
        onPressedSearch = { visibilitySF.value = !visibilitySF.value },
        onPressedFilter = { }
    )

    AnimatedVisibility(
        visible = visibilitySF.value,
        enter = slideInVertically( initialOffsetY = { -40 } ) + fadeIn(),
        exit = slideOutVertically( targetOffsetY = { -40 } ) + fadeOut()
    ) {
        ShowSearchField(
            text = searchQuery,
            placeholder = stringResource( R.string.search_episode ),
            onTextChanged = { viewModel.onSearchFieldChanged(it) }
        )
    }

    HorizontalDivider(
        thickness = 2.dp,
        color = MaterialTheme.colorScheme.surface,
        modifier = Modifier.padding( top = 10.dp )
    )
}