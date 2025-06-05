package com.galvezsh.rickandmortydb.presentation.charactersScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel

@Preview( showBackground = true, showSystemUi = true )
@Composable
fun CharactersScreen( viewModel: CharactersViewModel = hiltViewModel() ) {

    Box(
        modifier = Modifier.fillMaxSize().background( Color.LightGray ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Personajes",
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )
        }
    }

}