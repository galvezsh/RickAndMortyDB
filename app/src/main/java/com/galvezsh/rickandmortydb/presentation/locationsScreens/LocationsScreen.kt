package com.galvezsh.rickandmortydb.presentation.locationsScreens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import com.galvezsh.rickandmortydb.R
import com.galvezsh.rickandmortydb.domain.model.LocationModel
import com.galvezsh.rickandmortydb.presentation.shared.AppBottomNavigationBar
import com.galvezsh.rickandmortydb.presentation.shared.AppTopInfoBarLarge
import com.galvezsh.rickandmortydb.presentation.shared.ShowFooterBox
import com.galvezsh.rickandmortydb.presentation.shared.ShowPagingCases
import com.galvezsh.rickandmortydb.presentation.shared.ShowPagingItemListBox
import com.galvezsh.rickandmortydb.presentation.shared.ShowRowButton

@Composable
fun LocationsScreen(
    navController: NavHostController,
    currentDestination: NavDestination?,
    navigateToDetailLocation: (Int) -> Unit,
    viewModel: LocationsViewModel = hiltViewModel()
) {
    val from by viewModel.from.collectAsState()
    val to by viewModel.to.collectAsState()
    val locations = viewModel.locations.collectAsLazyPagingItems()
    val locationsCount = locations.itemCount
    val searchQuery by viewModel.searchQuery.collectAsState()
    var visibilitySF by remember { mutableStateOf( false ) }
    var visibilityFF by remember { mutableStateOf( false ) }

    LaunchedEffect( locationsCount ) { viewModel.onFromChanged( locationsCount ) }
    Scaffold(
        topBar = {
            AppTopInfoBarLarge(
                from = from,
                to = to,
                text = stringResource( R.string.locations ).uppercase(),
                placeholder = stringResource( R.string.search_location ),
                onPressedSearch = { visibilitySF = !visibilitySF },
                onPressedFilter = { visibilityFF = !visibilityFF  },
                visibilitySF = visibilitySF,
                searchQuery = searchQuery,
                onSearchFieldChanged = { viewModel.onSearchFieldChanged( it ) }
            )
        },
        bottomBar = {
            AppBottomNavigationBar(
                navController = navController,
                currentDestination = currentDestination
            )
        }
    ) { innerPadding ->
        Box( modifier = Modifier
            .padding(innerPadding)
            .fillMaxWidth() ) {
            Body(
                locations = locations,
                visibilityFF = visibilityFF,
                viewModel = viewModel,
                navigateToDetailLocation = { navigateToDetailLocation( it ) }
            )
        }
    }


}

@Composable
private fun Body(
    locations: LazyPagingItems<LocationModel>,
    visibilityFF: Boolean,
    viewModel: LocationsViewModel,
    navigateToDetailLocation: (Int) -> Unit
) {
    LazyColumn( modifier = Modifier.padding( horizontal = 20.dp ), contentPadding = PaddingValues( bottom = 16.dp ) ) {
        items(
            count = locations.itemCount,
            key = locations.itemKey { location -> location.id },
            contentType = locations.itemContentType { "location" }
        ) { index ->
            val location = locations[ index ]
            if ( location != null )
                PagingItemList( location ) { navigateToDetailLocation( it ) }
        }
    }
    ShowPagingCases( paging = locations, pagingCount = locations.itemCount )
    FilterBox( viewModel = viewModel, visibility = visibilityFF )
}

@Composable
private fun FilterBox( viewModel: LocationsViewModel, visibility: Boolean ) {

    val selectedIndexType by viewModel.selectedIndexType.collectAsState()
    val typeListText = listOf(
        stringResource( R.string.filterbox_character_all) + " ",
        stringResource( R.string.filterbox_location_planet ),
        stringResource( R.string.filterbox_location_dimension ),
        stringResource( R.string.filterbox_location_dream ) + " ",
        stringResource( R.string.filterbox_location_diegesis ),
        stringResource( R.string.filterbox_location_microverse ),
        stringResource( R.string.filterbox_location_space_station ),
        stringResource( R.string.filterbox_character_unknown )
    )
    val typeListData = listOf(
        "",
        "planet",
        "dimension",
        "dream",
        "diegesis",
        "microverse",
        "space station",
        "unknown"
    )

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
                text = stringResource( R.string.location_type ),
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSecondary,
                modifier = Modifier.align( Alignment.Start )
            )

            FlowRow( modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy( 8.dp ) ) {
                typeListText.forEachIndexed { index, textValue ->
                    val isSelected = ( index == selectedIndexType )
                    val weightValue = textValue.length.toFloat().coerceAtLeast(1f)

                    ShowRowButton(
                        textButton = typeListText[ index ],
                        isSelected = isSelected,
                        modifier = Modifier.weight( weightValue ),
                        onPressedButton = { viewModel.onTypeFilterChanged( newType = typeListData[ index ], newIndex = index ) }
                    )
                }
            }
        }
    }
}

@Composable
private fun PagingItemList( location: LocationModel, onPressedItemList: (Int) -> Unit ) {
    val listName = listOf(
        stringResource( R.string.location_type ),
        stringResource( R.string.location_dimension )
    )
    val listData = listOf( location.type, location.dimension )

    ShowPagingItemListBox( onClick = { onPressedItemList( location.id ) } ) {
        Row( modifier = Modifier.padding( 10.dp ), verticalAlignment = Alignment.CenterVertically ) {
            Column( modifier = Modifier
                .fillMaxHeight()
                .weight(1f), verticalArrangement = Arrangement.SpaceBetween ) {
                Text(
                    text = location.name,
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
                contentDescription = stringResource( R.string.navigate_to_detail_location ),
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}