package com.galvezsh.rickandmortydb.presentation.charactersScreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.galvezsh.rickandmortydb.R
import com.galvezsh.rickandmortydb.presentation.ShowHeader

@Preview( showBackground = true, showSystemUi = true )
@Composable
fun CharactersScreen( viewModel: CharactersViewModel = hiltViewModel() ) {

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ShowHeader(
                text = stringResource( R.string.characters ).uppercase(),
                onPressedSearch = { },
                onPressedFilter = { }
            )
        }
    }

}