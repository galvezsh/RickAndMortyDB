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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.galvezsh.rickandmortydb.R
import com.galvezsh.rickandmortydb.presentation.ShowHeader
import com.galvezsh.rickandmortydb.presentation.ShowSearchField
import com.galvezsh.rickandmortydb.presentation.ShowSpacer
import com.galvezsh.rickandmortydb.presentation.model.CharacterLocationModel
import com.galvezsh.rickandmortydb.presentation.model.CharacterModel

@Composable
fun CharactersScreen( viewModel: CharactersViewModel = hiltViewModel() ) {
    var visibilityFF by remember { mutableStateOf( false ) }
    val character = CharacterModel(
        id = 1,
        name = "Morty Smith",
        isAlive = true,
        species = "Humano",
        type = "",
        gender = "Masculino",
        origin = CharacterLocationModel("", ""),
        location = CharacterLocationModel("", ""),
        image = "https://rickandmortyapi.com/api/character/avatar/2.jpeg",
        episodes = emptyList<String>()
    )

    Box( modifier = Modifier.fillMaxSize() ) {

        Column( modifier = Modifier.padding( horizontal = 24.dp ).padding( top = 10.dp ) ) {
            Header( viewModel = viewModel, onPressedFilterButton = { visibilityFF = !visibilityFF } )
            HorizontalDivider(
                thickness = 2.dp,
                color = MaterialTheme.colorScheme.surface,
                modifier = Modifier.padding( top = 10.dp )
            )
            LazyColumn( contentPadding = PaddingValues( bottom = 16.dp )) {
                items( 10 ) {
                    ItemList( character = character, onPressedItemList = { characterID -> /** Make the navigation to detailCharacterScreen */ } )
                }
            }
        }

        AnimatedVisibility(
            visible = visibilityFF,
            enter = slideInVertically( initialOffsetY = { 160 } ) + fadeIn(),
            exit = slideOutVertically( targetOffsetY = { 160 } ) + fadeOut(),
            modifier = Modifier.align( Alignment.BottomCenter )
        ) {
            FilterBox(
                onGenderChanged = { viewModel.onGenderFilterChanged( it ) },
                onStatusChanged = { viewModel.onStatusFilterChanged( it )  }
            )
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
        enter = slideInVertically( initialOffsetY = { -40 } ) + fadeIn(),
        exit = slideOutVertically( targetOffsetY = { -40 } ) + fadeOut()
    ) {
        ShowSearchField(
            text = searchQuery,
            placeholder = stringResource( R.string.search_character ),
            onTextChanged = { viewModel.onSearchFieldChanged( it ) }
        )
    }
}

@Composable
private fun FilterBox( onGenderChanged: (String) -> Unit, onStatusChanged: (String) -> Unit ) {
    var selectedIndexGender by rememberSaveable { mutableIntStateOf( 0 ) }
    var selectedIndexStatus by rememberSaveable { mutableIntStateOf( 0 ) }
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
        stringResource( R.string.filterbox_character_dead )
    )
    val genderListData = listOf<String>( "", "male", "female", "genderless", "unknown" )
    val statusListData = listOf<String>( "", "alive", "dead" )

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
                    val isSelected = ( index == selectedIndexGender )

                    Button(
                        onClick = {
                            selectedIndexGender = index
                            onGenderChanged( genderListData[ index ] )
                        },
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
                    val isSelected = ( index == selectedIndexStatus )

                    Button(
                        onClick = {
                            selectedIndexStatus = index
                            onStatusChanged( statusListData[ index ] )
                        },
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
                        text =
                            if ( character.isAlive ) stringResource( R.string.filterbox_character_alive )
                            else stringResource( R.string.filterbox_character_dead ),
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSecondary
                    )
                }
            }

            Icon(
                imageVector = Icons.AutoMirrored.Default.ArrowForward,
                contentDescription = "",
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}