package com.galvezsh.rickandmortydb.presentation.charactersScreens

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.galvezsh.rickandmortydb.R
import com.galvezsh.rickandmortydb.domain.model.CharacterModel
import com.galvezsh.rickandmortydb.presentation.ShowErrorBox
import com.galvezsh.rickandmortydb.presentation.ShowSpacer
import com.galvezsh.rickandmortydb.presentation.showToast

@Composable
fun DetailCharacterScreen(
    navigateToDetailLocation: (Int) -> Unit,
    navigateToDetailEpisode: (Int) -> Unit,
    viewModel: DetailCharacterViewModel = hiltViewModel()
) {

    val character by viewModel.character.collectAsState()
    val context = LocalContext.current
    val text = stringResource( R.string.url_not_valid )

    ShowHeader( stringResource( R.string.detail_character ).uppercase() ) {
        if ( character != null )
            Body(
                character = character!!,
                navigateToDetailEpisode = { id -> navigateToDetailEpisode( id ) },
                navigateToDetailLocation = { id ->
                    if (id != null) navigateToDetailLocation( id )
                    else showInvalidUrl( context, text )
                }
            )
        else
            ShowErrorBox( text = stringResource( R.string.no_internet ) )
    }
}

@Composable
private fun ShowHeader( text: String, content: @Composable () -> Unit ) {
    Column( modifier = Modifier.fillMaxSize() ) {
        Column(
            modifier = Modifier.fillMaxWidth().padding( top = 14.dp ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = text,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.surface,
            )

            HorizontalDivider(
                thickness = 2.dp,
                color = MaterialTheme.colorScheme.surface,
                modifier = Modifier.padding( top = 14.dp )
            )
        }

        Box( modifier = Modifier.fillMaxSize() ) {
            content()
        }
    }
}

@Composable
private fun Body( character: CharacterModel, navigateToDetailLocation: (Int?) -> Unit, navigateToDetailEpisode: (Int) -> Unit ) {

    val propertiesLabel = listOf(
        stringResource( R.string.character_species ),
        stringResource( R.string.character_gender ),
        stringResource( R.string.character_status )
    )
    val propertiesData = listOf(
        character.species,
        character.gender,
        character.isAlive
    )
    val locationsLabel = listOf(
        stringResource( R.string.character_origin ),
        stringResource( R.string.character_location )
    )
    val locationsName = listOf( character.origin.name, character.location.name )
    val locationsData = listOf( character.origin.url, character.location.url )
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
            AsyncImage(
                model = character.image,
                contentDescription = stringResource( R.string.character_image_content ),
                modifier = Modifier.clip( CircleShape ).size( 160.dp ).border(
                    4.dp,
                    if ( character.isAlive.lowercase() == "alive" ) Color.Green
                    else if ( character.isAlive.lowercase() == "dead" ) Color.Red
                    else Color.Yellow,
                    CircleShape
                ),
            )
            ShowSpacer( 8.dp )
            Text(
                text = character.name,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.onPrimary,
            )

            ShowSpacer( 8.dp )

            Text(
                text = stringResource( R.string.character_properties ),
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onSecondary,
            )
            ShowSpacer( 8.dp )
            repeat( 3 ) { index ->
                Row( modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy( 4.dp ) ) {
                    Text(
                        text = propertiesLabel[ index ],
                        color = MaterialTheme.colorScheme.onSecondary,
                        modifier = modifierRowItem.weight( 0.6f ),
                    )
                    Box( modifier = modifierRowItem.weight( 1f ) ) {
                        Text(
                            text = propertiesData[ index ],
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colorScheme.onSecondary,
                            modifier = Modifier.align( Alignment.Center )
                        )
                    }
                }
                ShowSpacer( 4.dp )
            }

            ShowSpacer( 4.dp )
            Text(
                text = stringResource( R.string.character_locations ),
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onSecondary,
            )
            ShowSpacer( 8.dp )
            repeat( 2 ) { index ->
                Row(
                    modifier = Modifier.fillMaxWidth().height( IntrinsicSize.Min ),
                    horizontalArrangement = Arrangement.spacedBy( 4.dp ),
                ) {
                    Text(
                        text = locationsLabel[ index ],
                        color = MaterialTheme.colorScheme.onSecondary,
                        modifier = modifierRowItem.weight( 0.6f ).fillMaxHeight(),
                    )
                    Box( modifier = modifierRowItem.weight( 1f ).clickable{ navigateToDetailLocation( extractIdFromUrl( locationsData[ index ] ) ) } ) {
                        Row( verticalAlignment = Alignment.CenterVertically ) {
                            Text(
                                text = locationsName[ index ],
                                textAlign = TextAlign.Center,
                                color = MaterialTheme.colorScheme.onSecondary,
                                modifier = Modifier.weight( 1f )
                            )
                            Icon(
                                imageVector = Icons.AutoMirrored.Default.ArrowForward,
                                contentDescription = stringResource( R.string.navigate_to_detail_location ),
                                tint = MaterialTheme.colorScheme.onSecondary
                            )
                        }
                    }
                }
                ShowSpacer( 4.dp )
            }
            ShowSpacer( 4.dp )
            Text(
                text = stringResource( R.string.character_episodes ),
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onSecondary,
            )
            ShowSpacer( 8.dp )
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy( 4.dp ),
                verticalArrangement = Arrangement.spacedBy( 4.dp ),
            ) {
                repeat( character.episodes.size ) { index ->
                    var episodeId = extractIdFromUrl( character.episodes[ index ] )!! // If the are episodes, always is gonna be the Id present

                    Text(
                        text = "" + episodeId,
                        fontSize = 16.sp,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onSecondary,
                        modifier = modifierRowItem.size( 24.dp ).clickable { navigateToDetailEpisode( episodeId ) }
                    )
                }
            }
            ShowSpacer( 8.dp )
        }
    }
}

private fun extractIdFromUrl( url: String ) = url.substringAfterLast("/").toIntOrNull()

private fun showInvalidUrl( context: Context, text: String ) {
    showToast( context, text, true )
}
