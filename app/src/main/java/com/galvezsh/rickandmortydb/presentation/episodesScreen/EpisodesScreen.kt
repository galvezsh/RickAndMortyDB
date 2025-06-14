package com.galvezsh.rickandmortydb.presentation.episodesScreen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.galvezsh.rickandmortydb.R
import com.galvezsh.rickandmortydb.presentation.NotImplementedYet
import com.galvezsh.rickandmortydb.presentation.ShowBottomBox
import com.galvezsh.rickandmortydb.presentation.ShowHeader

@Composable
fun EpisodesScreen( viewModel: EpisodesViewModel = hiltViewModel() ) {

    val from by viewModel.from.collectAsState()
    val to by viewModel.to.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    var visibilitySF by remember { mutableStateOf( false ) }
    var visibilityFF by remember { mutableStateOf( false ) }

    ShowHeader(
        from = from,
        to = to,
        text = stringResource( R.string.episodes ).uppercase(),
        placeholder = stringResource( R.string.search_episode ),
        onPressedSearch = { visibilitySF = !visibilitySF },
        onPressedFilter = { visibilityFF = !visibilityFF  },
        visibilitySF = visibilitySF,
        searchQuery = searchQuery,
        onSearchFieldChanged = {  }
    ) {
        NotImplementedYet()
        FilterBox( viewModel = viewModel, visibility = visibilityFF )
    }
}

@Composable
private fun FilterBox( viewModel: EpisodesViewModel, visibility: Boolean ) {
    ShowBottomBox( visibility = visibility ) {
        NotImplementedYet()
    }
}