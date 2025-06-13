package com.galvezsh.rickandmortydb

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.galvezsh.rickandmortydb.core.navigation.NavigationWrapper
import com.galvezsh.rickandmortydb.core.ui.RickAndMortyDBTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            // Set the dynamicColor to false to no infer with Theme.kt
            RickAndMortyDBTheme( dynamicColor = false ) {
                NavigationWrapper()
            }
        }
    }
}