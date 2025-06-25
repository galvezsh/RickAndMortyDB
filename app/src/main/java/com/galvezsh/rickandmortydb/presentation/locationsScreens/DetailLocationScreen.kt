package com.galvezsh.rickandmortydb.presentation.locationsScreens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import com.galvezsh.rickandmortydb.domain.model.LocationModel
import com.galvezsh.rickandmortydb.presentation.ShowDataListFromDetail
import com.galvezsh.rickandmortydb.presentation.ShowErrorBox
import com.galvezsh.rickandmortydb.presentation.ShowHeader
import com.galvezsh.rickandmortydb.presentation.ShowLinearProgressBar
import com.galvezsh.rickandmortydb.presentation.ShowSpacer
import com.galvezsh.rickandmortydb.presentation.extractIdFromUrl
import com.galvezsh.rickandmortydb.presentation.showToast

@Composable
fun DetailLocationScreen( navigateToDetailCharacter: (Int) -> Unit, viewModel: DetailLocationViewModel = hiltViewModel() ) {

    val location by viewModel.location.collectAsState()
    val context = LocalContext.current
    val text = stringResource( R.string.url_not_valid )

    ShowHeader( stringResource( R.string.detail_location ).uppercase() ) {
        if ( location != null ) {
            ShowBody( location = location!! ) {
                ShowCharacterList(
                    location = location!!,
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
private fun ShowBody( location: LocationModel, content: @Composable () -> Unit ) {

    val modifierRowItem = Modifier
        .clip( RoundedCornerShape( 4.dp ) )
        .background( MaterialTheme.colorScheme.primary )
        .padding( 8.dp )
    val listName = listOf(
        stringResource( R.string.location_type ),
        stringResource( R.string.location_dimension )
    )
    val listData = listOf( location.type, location.dimension )

    Box( modifier = Modifier.fillMaxSize().padding( horizontal = 20.dp ) ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.verticalScroll( rememberScrollState() )
        ) {

            ShowSpacer( 8.dp )
            Text(
                text = location.name,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = MaterialTheme.colorScheme.onPrimary,
            )

            ShowSpacer( 8.dp )

            Text(
                text = stringResource( R.string.location_characteristics ),
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
                        text = listName[ index ],
                        color = MaterialTheme.colorScheme.onSecondary,
                        modifier = modifierRowItem.weight( 0.6f ).fillMaxHeight(),
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
                text = stringResource( R.string.location_residents ),
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
fun ShowCharacterList( location: LocationModel, viewModel: DetailLocationViewModel, navigateToDetailCharacter: (Int?) -> Unit ) {

    val characterTexts by viewModel.characterTexts.collectAsState()
    val modifierRowItem = Modifier
        .clip( RoundedCornerShape( 4.dp ) )
        .background( MaterialTheme.colorScheme.primary )
        .padding( 8.dp )

    LaunchedEffect( location ) { viewModel.loadCharacters( location.residents ) }

    if ( characterTexts.isEmpty() ) {
        ShowLinearProgressBar()
        ShowSpacer( 8.dp )
    } else {
        characterTexts.forEachIndexed { index, text ->
            ShowDataListFromDetail(
                text = text,
                modifier = modifierRowItem,
                onPressedRowItem = { navigateToDetailCharacter( extractIdFromUrl( location.residents[index] )!! /* Character ID */) }
            )
        }
        ShowSpacer(4.dp)
    }
}