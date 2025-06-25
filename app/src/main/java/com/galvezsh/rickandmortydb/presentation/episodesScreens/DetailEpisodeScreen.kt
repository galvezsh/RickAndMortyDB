package com.galvezsh.rickandmortydb.presentation.episodesScreens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.galvezsh.rickandmortydb.R
import com.galvezsh.rickandmortydb.domain.model.EpisodeModel
import com.galvezsh.rickandmortydb.presentation.ShowDataListFromDetail
import com.galvezsh.rickandmortydb.presentation.ShowErrorBox
import com.galvezsh.rickandmortydb.presentation.ShowHeader
import com.galvezsh.rickandmortydb.presentation.ShowLinearProgressBar
import com.galvezsh.rickandmortydb.presentation.ShowSpacer
import com.galvezsh.rickandmortydb.presentation.extractIdFromUrl
import com.galvezsh.rickandmortydb.presentation.parseEpisodeCode
import com.galvezsh.rickandmortydb.presentation.showToast

@Composable
fun DetailEpisodeScreen( navigateToDetailCharacter: (Int) -> Unit, viewModel: DetailEpisodeViewModel = hiltViewModel() ) {

    val episode by viewModel.episode.collectAsState()
    val context = LocalContext.current
    val text = stringResource( R.string.url_not_valid )

    ShowHeader( stringResource( R.string.detail_episode ).uppercase() ) {
        if ( episode != null ) {
            ShowBody( episode = episode!! ) {
                ShowCharacterList(
                    episode = episode!!,
                    viewModel = viewModel,
                    navigateToDetailCharacter = { id ->
                        if (id != null) navigateToDetailCharacter( id )
                        else showToast( context, text, true )
                    }
                )
            }
        } else {
            ShowErrorBox( text = stringResource( R.string.no_internet ) )
        }
    }
}

@Composable
private fun ShowBody( episode: EpisodeModel, content: @Composable () -> Unit ) {

    val ( seasonNumber, episodeNumber ) = parseEpisodeCode( episode.episode ) ?: Pair( 0, 0 )
    val listName = listOf(
        stringResource( R.string.episode_season ),
        stringResource( R.string.episode_episode )
    )
    val listData = listOf( seasonNumber.toString(), episodeNumber.toString() )
    val modifierRowItem = Modifier
        .clip( RoundedCornerShape( 4.dp ) )
        .background( MaterialTheme.colorScheme.primary )
        .padding( 8.dp )

    Box( modifier = Modifier.fillMaxSize().padding( horizontal = 20.dp ) ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.verticalScroll( rememberScrollState() )
        ) {

            ShowSpacer( 8.dp )
            Text(
                text = episode.name,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.onPrimary,
            )

            ShowSpacer( 8.dp )

            Text(
                text = stringResource( R.string.episode_details ),
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onSecondary,
            )
            ShowSpacer( 8.dp )
            repeat( 2 ) { index ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy( 4.dp ),
                ) {
                    Text(
                        text = listName[ index ],
                        color = MaterialTheme.colorScheme.onSecondary,
                        modifier = modifierRowItem.weight( 0.6f ),
                    )
                    Text(
                        text = listData[ index ],
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onSecondary,
                        modifier = modifierRowItem.weight( 1f )
                    )
                }
                ShowSpacer( 4.dp )
            }
            ShowSpacer( 4.dp )
            Text(
                text = stringResource( R.string.characters ),
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onSecondary,
            )
            ShowSpacer( 8.dp )
            content()
        }
    }
}

@Composable
fun ShowCharacterList( episode: EpisodeModel, viewModel: DetailEpisodeViewModel, navigateToDetailCharacter: (Int?) -> Unit ) {

    val characterTexts by viewModel.characterTexts.collectAsState()
    val modifierRowItem = Modifier
        .clip( RoundedCornerShape( 4.dp ) )
        .background( MaterialTheme.colorScheme.primary )
        .padding( 8.dp )

    LaunchedEffect( episode ) { viewModel.loadCharacters( episode.characters ) }

    if ( characterTexts.isEmpty() ) {
        ShowLinearProgressBar()
        ShowSpacer( 8.dp )
    } else {
        characterTexts.forEachIndexed { index, text ->
            ShowDataListFromDetail(
                text = text,
                modifier = modifierRowItem,
                onPressedRowItem = { navigateToDetailCharacter( extractIdFromUrl( episode.characters[index] )!! /* Character ID */) }
            )
        }
        ShowSpacer(4.dp)
    }
}