package com.galvezsh.rickandmortydb.presentation.episodesScreens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.galvezsh.rickandmortydb.R
import com.galvezsh.rickandmortydb.domain.model.EpisodeModel
import com.galvezsh.rickandmortydb.presentation.ShowFooterBox
import com.galvezsh.rickandmortydb.presentation.ShowFullHeader
import com.galvezsh.rickandmortydb.presentation.ShowPagingCases
import com.galvezsh.rickandmortydb.presentation.ShowPagingItemListBox
import com.galvezsh.rickandmortydb.presentation.ShowRowButton
import com.galvezsh.rickandmortydb.presentation.parseEpisodeCode

@Composable
fun EpisodesScreen( navigateToDetailEpisode: (Int) -> Unit, viewModel: EpisodesViewModel = hiltViewModel() ) {

    val from by viewModel.from.collectAsState()
    val to by viewModel.to.collectAsState()
    val episodes = viewModel.episodes.collectAsLazyPagingItems()
    val episodesCount = episodes.itemCount
    val searchQuery by viewModel.searchQuery.collectAsState()
    var visibilitySF by remember { mutableStateOf( false ) }
    var visibilityFF by remember { mutableStateOf( false ) }

    LaunchedEffect( episodesCount ) { viewModel.onFromChanged( episodesCount ) }

    ShowFullHeader(
        from = from,
        to = to,
        text = stringResource( R.string.episodes ).uppercase(),
        placeholder = stringResource( R.string.search_episode ),
        onPressedSearch = { visibilitySF = !visibilitySF },
        onPressedFilter = { visibilityFF = !visibilityFF  },
        visibilitySF = visibilitySF,
        searchQuery = searchQuery,
        onSearchFieldChanged = { viewModel.onSearchFieldChanged( it ) }
    ) {
        LazyColumn( modifier = Modifier.padding( horizontal = 20.dp ), contentPadding = PaddingValues( bottom = 16.dp ) ) {
            items( episodesCount ) { index ->
                episodes[ index ]?.let { episode ->
                    PagingItemList( episode ) { navigateToDetailEpisode( it ) }
                }
            }
        }
        ShowPagingCases( paging = episodes, pagingCount = episodesCount )
        FilterBox( viewModel = viewModel, visibility = visibilityFF )
    }
}

@Composable
private fun FilterBox( viewModel: EpisodesViewModel, visibility: Boolean ) {
    val selectedIndexSeason by viewModel.seasonIndex.collectAsState()
    val seasonListText = listOf(
        stringResource( R.string.episode_all ),
        stringResource( R.string.episode_season ) + " 1",
        stringResource( R.string.episode_season ) + " 2",
        stringResource( R.string.episode_season ) + " 3",
        stringResource( R.string.episode_season ) + " 4",
        stringResource( R.string.episode_season ) + " 5",
    )
    val seasonListData = listOf( "", "s01", "s02", "s03", "s04", "s05" )

    ShowFooterBox( visibility = visibility ) {
        Column(
            modifier = Modifier.fillMaxWidth().wrapContentHeight().padding( 8.dp ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource( R.string.filterbox_label ) + ":",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimary
            )

            Text(
                text = stringResource( R.string.character_gender ),
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSecondary,
                modifier = Modifier.align( Alignment.Start )
            )

            FlowRow( modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy( 8.dp ) ) {
                repeat( seasonListText.size ) { index ->
                    val isSelected = ( index == selectedIndexSeason )

                    ShowRowButton(
                        textButton = seasonListText[ index ],
                        isSelected = isSelected,
                        modifier = Modifier.weight( 1f ),
                        onPressedButton = { viewModel.onSeasonFilterChanged( newSeason = seasonListData[ index ], newIndex = index ) }
                    )
                }
            }
        }
    }
}

@Composable
private fun PagingItemList( episode: EpisodeModel, onPressedItemList: (Int) -> Unit ) {
    val ( seasonNumber, episodeNumber ) = parseEpisodeCode( episode.episode ) ?: Pair( 0, 0 )
    val listName = listOf(
        stringResource( R.string.episode_season ),
        stringResource( R.string.episode_episode )
    )
    val listData = listOf( seasonNumber.toString(), episodeNumber.toString() )

    ShowPagingItemListBox( onClick = { onPressedItemList( episode.id ) } ) {
        Row( modifier = Modifier.padding( 10.dp ), verticalAlignment = Alignment.CenterVertically ){
            Column(
                modifier = Modifier.fillMaxHeight().weight( 1f ),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = episode.name,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimary
                )

                repeat( 2 ) { index ->
                    Row {
                        Text(
                            text = listName[ index ] + ": ",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onSecondary
                        )
                        Text(
                            text = listData[ index ],
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.onSecondary
                        )
                    }
                }
            }

            Icon(
                imageVector = Icons.AutoMirrored.Default.ArrowForward,
                contentDescription = stringResource( R.string.navigate_to_detail_episode ),
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}