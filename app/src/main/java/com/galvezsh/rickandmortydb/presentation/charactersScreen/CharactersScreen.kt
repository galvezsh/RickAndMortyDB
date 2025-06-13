package com.galvezsh.rickandmortydb.presentation.charactersScreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.galvezsh.rickandmortydb.R
import com.galvezsh.rickandmortydb.domain.model.CharacterModel
import com.galvezsh.rickandmortydb.presentation.ShowCircularProgressBar
import com.galvezsh.rickandmortydb.presentation.ShowErrorBox
import com.galvezsh.rickandmortydb.presentation.ShowErrorBoxWithButton
import com.galvezsh.rickandmortydb.presentation.ShowHeader
import com.galvezsh.rickandmortydb.presentation.ShowSearchField
import com.galvezsh.rickandmortydb.presentation.ShowSpacer
import com.galvezsh.rickandmortydb.presentation.ShowToast

@Composable
fun CharactersScreen( viewModel: CharactersViewModel = hiltViewModel() ) {

    val characters = viewModel.characters.collectAsLazyPagingItems()
    var visibilityFF by remember { mutableStateOf( false ) }
    val numberOfCharacters = characters.itemCount

    // With launchedEffect, the internal code will only be executed when the value passed as a
    // parameter changes, optimizing the code since it avoids having to execute this code for
    // each recomposition of this screen.
    LaunchedEffect( numberOfCharacters ) {
        viewModel.onFromChanged( numberOfCharacters )
    }

    Box( modifier = Modifier.fillMaxSize() ) {
        Column( modifier = Modifier.padding( horizontal = 24.dp ).padding( top = 10.dp ) ) {

            Header(
                viewModel = viewModel,
                onPressedFilterButton = { visibilityFF = !visibilityFF }
            )

            LazyColumn( contentPadding = PaddingValues( bottom = 16.dp ) ) {
                items( numberOfCharacters ) { index ->

                    // Checking if the character in the index position is NOT null, for safety
                    characters[index]?.let { character ->
                        ItemList(
                            character = character,
                            onPressedItemList = { characterID ->
                                /** Navegación a detalle */
                            }
                        )
                    }
                }
            }
        }

        when {
            characters.loadState.refresh is LoadState.NotLoading && numberOfCharacters == 0 -> {
                Box( modifier = Modifier.align( Alignment.Center ) ) {
                    ShowErrorBox( "No hay datos" )
                }
            }

            characters.loadState.hasError -> {
                ShowErrorBoxWithButton(
                    text = "Se ha producido un error inesperado, intentelo de nuevo o pruebe mas tarde",
                    textButton = "Reintentar",
                    onPressedButton = { characters.retry() }
                )
            }

            characters.loadState.refresh is LoadState.Loading && numberOfCharacters == 0 ||
                    characters.loadState.append is LoadState.Loading -> {
                ShowCircularProgressBar()
            }
        }

        // Animation for the FilterBox when is own button is pressed
        AnimatedVisibility(
            visible = visibilityFF,
            enter = slideInVertically( initialOffsetY = { 160 }) + fadeIn(),
            exit = slideOutVertically( targetOffsetY = { 160 }) + fadeOut(),
            modifier = Modifier.align(Alignment.BottomCenter)
        ) {
            FilterBox( viewModel = viewModel )
        }
    }
}

@Composable
private fun Header( viewModel: CharactersViewModel, onPressedFilterButton: () -> Unit ) {

    val from by viewModel.from.collectAsState()
    val to by viewModel.to.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    var visibilitySF by remember { mutableStateOf( false ) }

    ShowHeader(
        text = stringResource( R.string.characters ).uppercase(),
        from = from,
        to = to,
        onPressedSearch = { visibilitySF = !visibilitySF },
        onPressedFilter = { onPressedFilterButton() }
    )

    AnimatedVisibility(
        visible = visibilitySF,
        enter = slideInVertically(initialOffsetY = { -40 }) + fadeIn(),
        exit = slideOutVertically(targetOffsetY = { -40 }) + fadeOut()
    ) {
        ShowSearchField(
            text = searchQuery,
            placeholder = stringResource( R.string.search_character ),
            onTextChanged = { viewModel.onSearchFieldChanged( it ) }
        )
    }

    HorizontalDivider(
        thickness = 2.dp,
        color = MaterialTheme.colorScheme.surface,
        modifier = Modifier.padding( top = 10.dp )
    )
}

@Composable
private fun FilterBox( viewModel: CharactersViewModel ) {
    val selectedIndexGender = viewModel.genderIndex.collectAsState()
    val selectedIndexStatus = viewModel.statusIndex.collectAsState()
    val genderListText = listOf<String>(
        stringResource( R.string.filterbox_character_all ),
        stringResource( R.string.filterbox_character_male ),
        stringResource( R.string.filterbox_character_female ),
        stringResource( R.string.filterbox_character_genderless ),
        stringResource( R.string.filterbox_character_unknown )
    )
    val statusListText = listOf<String>(
        stringResource( R.string.filterbox_character_all ),
        stringResource( R.string.filterbox_character_alive ),
        stringResource( R.string.filterbox_character_dead ),
        stringResource( R.string.filterbox_character_unknown ),
    )
    val genderListData = listOf<String>( "", "male", "female", "genderless", "unknown" )
    val statusListData = listOf<String>( "", "alive", "dead", "unknown" )

    Column {
        HorizontalDivider( thickness = 2.dp, color = MaterialTheme.colorScheme.surface )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background( MaterialTheme.colorScheme.background )
                .padding( 8.dp ),
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
                repeat( genderListText.size ) { index ->
                    val isSelected = ( index == selectedIndexGender.value )

                    Button(
                        onClick = { viewModel.onGenderFilterChanged( newGender = genderListData[ index ], index ) },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape( 4.dp ),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (isSelected) MaterialTheme.colorScheme.surfaceVariant else MaterialTheme.colorScheme.surface,
                            contentColor = MaterialTheme.colorScheme.background
                        )

                    ) { Text( text = genderListText[ index ] ) }
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
                repeat( statusListText.size ) { index ->
                    val isSelected = ( index == selectedIndexStatus.value )

                    Button(
                        onClick = { viewModel.onStatusFilterChanged( statusListData[ index ], index ) },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape( 4.dp ),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (isSelected) MaterialTheme.colorScheme.surfaceVariant else MaterialTheme.colorScheme.surface,
                            contentColor = MaterialTheme.colorScheme.background
                        )

                    ) { Text( text = statusListText[ index ] ) }
                }
            }
        }
    }
}

@Composable
private fun ItemList( character: CharacterModel, onPressedItemList: (Int) -> Unit ) {
    Box( modifier = Modifier
        .padding( top = 16.dp )
        .clip( RoundedCornerShape( 12.dp ))
        .border( 2.dp, MaterialTheme.colorScheme.surface, RoundedCornerShape( 12.dp ) )
        .height( 128.dp )
        .fillMaxWidth()
        .background( MaterialTheme.colorScheme.primary )
        .clickable { onPressedItemList( character.id ) }
    ) {
        Row( modifier = Modifier.padding( 10.dp ), verticalAlignment = Alignment.CenterVertically ) {
            AsyncImage(
                model = character.image,
                contentDescription = stringResource( R.string.character_image_content ),
                modifier = Modifier.clip(RoundedCornerShape( 6.dp ) ),
                contentScale = ContentScale.Crop
            )
            ShowSpacer( 5.dp )
            Column( modifier = Modifier.fillMaxHeight().weight( 1f ), verticalArrangement = Arrangement.SpaceBetween ) {
                Text(
                    text = character.name,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimary
                )

                Row {
                    Text(
                        text = stringResource( R.string.character_species ) + ": ",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSecondary
                    )
                    Text(
                        text = character.species,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSecondary
                    )
                }

                Row {
                    Text(
                        text = stringResource( R.string.character_gender ) + ": ",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSecondary
                    )
                    Text(
                        text = character.gender,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSecondary
                    )
                }

                Row {
                    Text(
                        text = stringResource( R.string.character_status ) + ": ",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.onSecondary
                    )
                    Text(
                        text = character.isAlive,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSecondary
                    )
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