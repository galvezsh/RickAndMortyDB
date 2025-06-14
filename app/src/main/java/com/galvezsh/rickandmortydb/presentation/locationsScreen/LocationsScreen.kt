package com.galvezsh.rickandmortydb.presentation.locationsScreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.galvezsh.rickandmortydb.R
import com.galvezsh.rickandmortydb.presentation.NotImplementedYet
import com.galvezsh.rickandmortydb.presentation.ShowBottomBox
import com.galvezsh.rickandmortydb.presentation.ShowHeader

@Composable
fun LocationsScreen( viewModel: LocationsViewModel = hiltViewModel() ) {

    val from by viewModel.from.collectAsState()
    val to by viewModel.to.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    var visibilitySF by remember { mutableStateOf( false ) }
    var visibilityFF by remember { mutableStateOf( false ) }

    ShowHeader(
        from = from,
        to = to,
        text = stringResource( R.string.locations ).uppercase(),
        placeholder = stringResource( R.string.search_location ),
        onPressedSearch = { visibilitySF = !visibilitySF },
        onPressedFilter = { visibilityFF = !visibilityFF  },
        visibilitySF = visibilitySF,
        searchQuery = searchQuery,
        onSearchFieldChanged = {  }
    ) {
        Box( modifier = Modifier.fillMaxSize() ) {
            Text(
                text = stringResource( R.string.not_implemented ),
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.surface,
                modifier = Modifier.align( Alignment.Center )
            )
        }
        NotImplementedYet()
        FilterBox( viewModel = viewModel, visibility = visibilityFF )
    }
}

@Composable
private fun FilterBox( viewModel: LocationsViewModel, visibility: Boolean ) {
    ShowBottomBox( visibility = visibility ) {
        NotImplementedYet()
    }
}