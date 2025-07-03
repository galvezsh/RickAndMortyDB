package com.galvezsh.rickandmortydb.presentation.charactersScreens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavDestination
import androidx.navigation.NavHostController
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import coil.compose.AsyncImage
import com.galvezsh.rickandmortydb.R
import com.galvezsh.rickandmortydb.domain.model.CharacterModel
import com.galvezsh.rickandmortydb.presentation.shared.AppBottomNavigationBar
import com.galvezsh.rickandmortydb.presentation.shared.AppTopInfoBarLarge
import com.galvezsh.rickandmortydb.presentation.shared.ShowFooterBox
import com.galvezsh.rickandmortydb.presentation.shared.ShowPagingCases
import com.galvezsh.rickandmortydb.presentation.shared.ShowPagingItemListBox
import com.galvezsh.rickandmortydb.presentation.shared.ShowRowButton
import com.galvezsh.rickandmortydb.presentation.shared.ShowSpacer

@Composable
fun CharactersScreen(
    navController: NavHostController,
    currentDestination: NavDestination?,
    navigateToDetailCharacter: (Int) -> Unit,
    viewModel: CharacterViewModel = hiltViewModel()
) {
    val from by viewModel.from.collectAsState()
    val to by viewModel.to.collectAsState()
    val characters = viewModel.characters.collectAsLazyPagingItems()
    val charactersCount = characters.itemCount
    val searchQuery by viewModel.searchQuery.collectAsState()
    var visibilitySF by remember { mutableStateOf( false ) }
    var visibilityFF by remember { mutableStateOf( false ) }

    // With launchedEffect, the internal code will only be executed when the value passed as a
    // parameter changes, optimizing the code since it avoids having to execute this code for
    // each recomposition of this screen.
    LaunchedEffect( charactersCount ) { viewModel.onFromChanged( charactersCount ) }
    Scaffold(
        topBar = { AppTopInfoBarLarge(
            from = from,
            to = to,
            text = stringResource( R.string.characters ).uppercase(),
            placeholder = stringResource( R.string.search_character ),
            onPressedSearch = { visibilitySF = !visibilitySF },
            onPressedFilter = { visibilityFF = !visibilityFF  },
            visibilitySF = visibilitySF,
            searchQuery = searchQuery,
            onSearchFieldChanged = { viewModel.onSearchFieldChanged( it ) }
        ) },
        bottomBar = {
            AppBottomNavigationBar(
                navController = navController,
                currentDestination = currentDestination
            )
        }
    ) { innerPadding ->
        Box( modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize() ) {
            Body(
                characters = characters,
                visibilityFF = visibilityFF,
                viewModel = viewModel,
                navigateToDetailCharacter = { navigateToDetailCharacter( it ) }
            )
        }
    }
}

@Composable
private fun Body(
    characters: LazyPagingItems<CharacterModel>,
    visibilityFF: Boolean,
    viewModel: CharacterViewModel,
    navigateToDetailCharacter: (Int) -> Unit
) {
    LazyColumn( modifier = Modifier.padding( horizontal = 20.dp ), contentPadding = PaddingValues( bottom = 16.dp ) ) {
        items(
            count = characters.itemCount,
            key = characters.itemKey { character -> character.id },
            contentType = characters.itemContentType { "character" }
        ) { index ->
            val character = characters[ index ]
            if ( character != null )
                PagingItemList( character ) { navigateToDetailCharacter( it ) }
        }
    }
    ShowPagingCases( paging = characters, pagingCount = characters.itemCount )
    FilterBox( viewModel = viewModel, visibility = visibilityFF )
}

@Composable
private fun FilterBox( viewModel: CharacterViewModel, visibility: Boolean ) {
    val selectedIndexGender by viewModel.genderIndex.collectAsState()
    val selectedIndexStatus by viewModel.statusIndex.collectAsState()
    val genderListText = listOf(
        stringResource( R.string.filterbox_character_all ) + " ",
        stringResource( R.string.filterbox_character_male ),
        stringResource( R.string.filterbox_character_female ),
        stringResource( R.string.filterbox_character_genderless ),
        stringResource( R.string.filterbox_character_unknown )
    )
    val statusListText = listOf(
        stringResource( R.string.filterbox_character_all ) + " ",
        stringResource( R.string.filterbox_character_alive ),
        stringResource( R.string.filterbox_character_dead ) + " ",
        stringResource( R.string.filterbox_character_unknown ),
    )
    val genderListData = listOf( "", "male", "female", "genderless", "unknown" )
    val statusListData = listOf( "", "alive", "dead", "unknown" )

    ShowFooterBox( visibility = visibility ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(8.dp),
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
                genderListText.forEachIndexed { index, textValue ->
                    val isSelected = ( index == selectedIndexGender )
                    val weightValue = textValue.length.toFloat().coerceAtLeast(1f)

                    ShowRowButton(
                        textButton = genderListText[ index ],
                        isSelected = isSelected,
                        modifier = Modifier.weight( weightValue ),
                        onPressedButton = { viewModel.onGenderFilterChanged( newGender = genderListData[ index ], index ) }
                    )
                }
            }

            ShowSpacer( 4.dp )

            Text(
                text = stringResource( R.string.character_status ),
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSecondary,
                modifier = Modifier.align( Alignment.Start )
            )

            FlowRow( modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy( 8.dp ) ) {
                statusListText.forEachIndexed { index, textValue ->
                    val isSelected = ( index == selectedIndexStatus )
                    val weightValue = textValue.length.toFloat().coerceAtLeast(1f)

                    ShowRowButton(
                        textButton = statusListText[ index ],
                        isSelected = isSelected,
                        modifier = Modifier.weight( weightValue ),
                        onPressedButton = { viewModel.onStatusFilterChanged( newStatus = statusListData[ index ], index ) }
                    )
                }
            }
        }
    }
}

@Composable
private fun PagingItemList( character: CharacterModel, onPressedItemList: (Int) -> Unit ) {
    val listName = listOf(
        stringResource( R.string.character_species ),
        stringResource( R.string.character_gender ),
        stringResource( R.string.character_status )
    )
    val listData = listOf( character.species, character.gender, character.isAlive )

    ShowPagingItemListBox( onClick = { onPressedItemList( character.id ) } ) {
        Row( modifier = Modifier.padding( 10.dp ), verticalAlignment = Alignment.CenterVertically ) {
            AsyncImage(
                model = character.image,
                contentDescription = stringResource( R.string.character_image_content ),
                modifier = Modifier.clip( RoundedCornerShape( 6.dp ) ),
                contentScale = ContentScale.Crop
            )
            ShowSpacer( 5.dp )
            Column( modifier = Modifier
                .fillMaxHeight()
                .weight(1f), verticalArrangement = Arrangement.SpaceBetween ) {
                Text(
                    text = character.name,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimary
                )

                repeat( 3 ) { index ->
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
                contentDescription = stringResource( R.string.navigate_to_detail_character ),
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}