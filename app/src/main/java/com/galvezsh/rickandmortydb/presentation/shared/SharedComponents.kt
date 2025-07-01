package com.galvezsh.rickandmortydb.presentation.shared

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.rounded.FilterAlt
import androidx.compose.material.icons.rounded.FormatListNumbered
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.Place
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.galvezsh.rickandmortydb.R
import com.galvezsh.rickandmortydb.presentation.navigation.CharactersScreenSerial
import com.galvezsh.rickandmortydb.presentation.navigation.EpisodesScreenSerial
import com.galvezsh.rickandmortydb.presentation.navigation.LocationsScreenSerial
import com.galvezsh.rickandmortydb.presentation.navigation.NavigationBottomRoute

// In this file are defined some composable functions that can be use in multiples screens, like in
// React, because Jetpack Compose works using the same principle, building the interface using
// components. Thanks to this principle, some functions can be recycled, making the code much faster
// and cleaner

////////////////////////////////////////////////////////////////////////////////////////////////////
// GENERICS ////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////
@Composable
fun ShowSpacer( dp: Dp ) {
    Spacer( modifier = Modifier.padding(dp) )
}

@Composable
fun ShowCircularProgressBar() {
    Box( modifier = Modifier.fillMaxSize() ) {
        CircularProgressIndicator(
            modifier = Modifier.align( Alignment.Center ),
            color = MaterialTheme.colorScheme.surface
        )
    }
}

@Composable
fun ShowLinearProgressBar() {
    Box( modifier = Modifier.fillMaxWidth().padding( bottom = 8.dp ) ) {
        Column( horizontalAlignment = Alignment.CenterHorizontally ) {
            Text(
                text = stringResource( R.string.loading ),
                modifier = Modifier.padding( bottom = 6.dp ),
                color = MaterialTheme.colorScheme.onSecondary
            )
            LinearProgressIndicator(
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.surface,
                trackColor = MaterialTheme.colorScheme.primary
            )
        }
    }
}

////////////////////////////////////////////////////////////////////////////////////////////////////
// NAVIGATION //////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////
/**
 * This function build the bottom navigation bar with 4 buttons; characters, episodes, locations and settings,
 * where each button navigate to is own screen
 *
 * @param navController The navigation controller that allows the navigation between screens
 * @param currentDestination The current position when the app starts
 */
@Composable
fun AppBottomNavigationBar( navController: NavHostController, currentDestination: NavDestination? ) {

    val borderColor = MaterialTheme.colorScheme.surface
    // This is the list of the routes for the physical buttons in the bar when i describes the icon for the button,
    // the bottom text of the button and the route that takes when the user press this button
    val navigationBottomRoutes = listOf(
        NavigationBottomRoute(Icons.Rounded.Person, stringResource(R.string.characters), CharactersScreenSerial),
        NavigationBottomRoute(Icons.Rounded.FormatListNumbered, stringResource(R.string.episodes), EpisodesScreenSerial),
        NavigationBottomRoute(Icons.Rounded.Place, stringResource(R.string.locations), LocationsScreenSerial),
    )

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.background,
        modifier = Modifier.drawBehind {
            drawLine(
                color = borderColor,
                start = Offset(0f, 0f),
                end = Offset(size.width, 0f),
                strokeWidth = 5.dp.toPx()
            )
        }
    ) {
        // And this is where you go through the list to build each button
        navigationBottomRoutes.forEach { item ->
            NavigationBarItem(
                icon = { Icon(item.icon, contentDescription = item.name) },
                label = { Text(item.name) },
                selected = currentDestination?.hierarchy?.any { it.hasRoute(item.route::class) } == true,
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.surfaceVariant,
                    unselectedIconColor = MaterialTheme.colorScheme.surface,
                    selectedTextColor = MaterialTheme.colorScheme.surfaceVariant,
                    unselectedTextColor = MaterialTheme.colorScheme.surface,
                    indicatorColor = MaterialTheme.colorScheme.surfaceVariant.copy(0.2f)
                ),
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}

////////////////////////////////////////////////////////////////////////////////////////////////////
// PAGING CASES ////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////
@Composable
fun<T: Any> ShowPagingCases( paging: LazyPagingItems<T>, pagingCount: Int ) {
    when {
        paging.loadState.refresh is LoadState.NotLoading && pagingCount == 0 -> {
            ShowErrorBox( stringResource( R.string.no_data ) )
        }

        paging.loadState.hasError -> {
            ShowErrorBoxWithButton(
                text = stringResource( R.string.no_internet ),
                textButton = stringResource( R.string.retry ),
                onPressedButton = { paging.retry() }
            )
        }

        paging.loadState.refresh is LoadState.Loading && pagingCount == 0 ||
                paging.loadState.append is LoadState.Loading -> {
            ShowCircularProgressBar()
        }
    }
}

////////////////////////////////////////////////////////////////////////////////////////////////////
// HEADERS /////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////
@Composable
fun AppTopInfoBar( text: String ) {
    Column(
        modifier = Modifier.fillMaxWidth().statusBarsPadding().padding( top = 4.dp ),
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
}

@Composable
fun AppTopInfoBarLarge(
    from: Int,
    to: Int,
    text: String,
    placeholder: String,
    onPressedSearch: () -> Unit,
    onPressedFilter: () -> Unit,
    visibilitySF: Boolean,
    searchQuery: String,
    onSearchFieldChanged: (String) -> Unit
) {
    Column( modifier = Modifier.fillMaxWidth().statusBarsPadding() ) {
        Box(modifier = Modifier.fillMaxWidth().padding( horizontal = 20.dp) ) {
            Text(
                text = "$from " + stringResource( R.string.of ) + " $to",
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.surface,
                modifier = Modifier.align( Alignment.CenterStart )
            )

            Text(
                text = text,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.surface,
                modifier = Modifier.align( Alignment.Center )
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.align( Alignment.CenterEnd )
            ) {
                IconButton(
                    onClick = { onPressedSearch() },
                    modifier = Modifier.size( 32.dp )
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Search,
                        contentDescription = stringResource( R.string.icon_search ),
                        tint = MaterialTheme.colorScheme.surface
                    )
                }
                ShowSpacer( 2.dp )
                IconButton(
                    onClick = { onPressedFilter() },
                    modifier = Modifier.size( 32.dp )
                ) {
                    Icon(
                        imageVector = Icons.Rounded.FilterAlt,
                        contentDescription = stringResource( R.string.icon_filter ),
                        tint = MaterialTheme.colorScheme.surface
                    )
                }
            }
        }

        AnimatedVisibility(
            visible = visibilitySF,
            enter = slideInVertically( initialOffsetY = { -40 }) + fadeIn(),
            exit = slideOutVertically( targetOffsetY = { -40 }) + fadeOut()
        ) {
            ShowSearchField(
                text = searchQuery,
                placeholder = placeholder,
                onTextChanged = { onSearchFieldChanged( it ) }
            )
        }

        HorizontalDivider(
            thickness = 2.dp,
            color = MaterialTheme.colorScheme.surface,
            modifier = Modifier.padding( top = 10.dp )
        )
    }
}

@Composable
private fun ShowSearchField( text: String, placeholder: String, onTextChanged: (String) -> Unit ) {
    TextField(
        value = text,
        enabled = true,
        onValueChange = { onTextChanged( it ) },
        modifier = Modifier
            .fillMaxWidth()
            .padding( vertical = 8.dp ).padding( horizontal = 20.dp )
            .border( 2.dp, MaterialTheme.colorScheme.surface, RoundedCornerShape( 12.dp ) ),
        shape = RoundedCornerShape( 12.dp ),
        placeholder = { Text( text = placeholder ) },
        keyboardOptions = KeyboardOptions( keyboardType = KeyboardType.Text ),
        singleLine = true,
        colors = TextFieldDefaults.colors(
            focusedTextColor = MaterialTheme.colorScheme.onPrimary,
            unfocusedTextColor = MaterialTheme.colorScheme.onPrimary,
            focusedPlaceholderColor = MaterialTheme.colorScheme.surface,
            unfocusedPlaceholderColor = MaterialTheme.colorScheme.surface,
            focusedContainerColor = MaterialTheme.colorScheme.primary,
            unfocusedContainerColor = MaterialTheme.colorScheme.primary,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent
        )
    )
}

////////////////////////////////////////////////////////////////////////////////////////////////////
// FOOTER //////////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////
@Composable
fun ShowFooterBox(visibility: Boolean, content: @Composable () -> Unit ) {
    Box( modifier = Modifier.fillMaxSize() ) {
        AnimatedVisibility(
            visible = visibility,
            enter = slideInVertically( initialOffsetY = { 160 }) + fadeIn(),
            exit = slideOutVertically( targetOffsetY = { 160 }) + fadeOut(),
            modifier = Modifier.align( Alignment.BottomCenter )
        ) {
            Column( modifier = Modifier.background( MaterialTheme.colorScheme.background ) ) {
                HorizontalDivider( thickness = 2.dp, color = MaterialTheme.colorScheme.surface )
                content()
            }
        }
    }
}

@Composable
fun ShowRowButton( textButton: String, isSelected: Boolean, modifier: Modifier, onPressedButton: () -> Unit ) {
    Button(
        onClick = { onPressedButton() },
        modifier = modifier,
        shape = RoundedCornerShape( 4.dp ),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelected) MaterialTheme.colorScheme.surfaceVariant else MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.background
        )

    ) { Text(
        text = textButton,
        softWrap = false,
        maxLines = 1
    ) }
}

////////////////////////////////////////////////////////////////////////////////////////////////////
// LIST SCREEN /////////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////
@Composable
fun ShowPagingItemListBox(onClick: () -> Unit, content: @Composable () -> Unit ) {
    Box( modifier = Modifier
        .padding( top = 16.dp )
        .clip( RoundedCornerShape( 12.dp ))
        .border( 2.dp, MaterialTheme.colorScheme.surface, RoundedCornerShape( 12.dp ) )
        .height( 128.dp )
        .fillMaxWidth()
        .background( MaterialTheme.colorScheme.primary )
        .clickable { onClick() }
    ) { content() }
}

////////////////////////////////////////////////////////////////////////////////////////////////////
// DETAIL SCREEN ///////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////
@Composable
fun ShowDataListFromDetail( text: String, modifier: Modifier, onPressedRowItem: () -> Unit  ) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.clickable { onPressedRowItem() }
    ) {
        Text(
            text = text,
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onSecondary,
            modifier = Modifier.weight(1f)
        )
        Icon(
            imageVector = Icons.AutoMirrored.Default.ArrowForward,
            contentDescription = stringResource(R.string.navigate_to_detail_character),
            tint = MaterialTheme.colorScheme.onSecondary
        )
    }

    ShowSpacer(4.dp)
}

////////////////////////////////////////////////////////////////////////////////////////////////////
// ERROR WARNINGS //////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////////////////////////
@Composable
fun ShowErrorBox( text: String ) {
    Box( modifier = Modifier.fillMaxSize().padding( horizontal = 20.dp ) ) {
        Text(
            text = text,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier
                .align( Alignment.Center )
                .fillMaxWidth()
                .clip( RoundedCornerShape( 12.dp ) )
                .border( 2.dp, MaterialTheme.colorScheme.surface, RoundedCornerShape( 12.dp ) )
                .background( MaterialTheme.colorScheme.primary )
                .padding( 16.dp )
        )
    }
}

@Composable
fun ShowErrorBoxWithButton( text: String, textButton: String, onPressedButton: () -> Unit ) {
    Box( modifier = Modifier.fillMaxSize() ) {
        Box( modifier = Modifier.fillMaxSize().alpha( 0.3f ).background( Color.Black ) )
        Box( modifier = Modifier.fillMaxSize().padding( horizontal = 20.dp ) ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .align( Alignment.Center )
                    .clip( RoundedCornerShape( 12.dp ) )
                    .border( 2.dp, MaterialTheme.colorScheme.surface, RoundedCornerShape( 12.dp ) )
                    .background( MaterialTheme.colorScheme.primary )
                    .padding( 16.dp )
                    .fillMaxWidth()
            ) {
                Text(
                    text = text,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onPrimary,
                )
                ShowSpacer( 6.dp )
                Button(
                    onClick = { onPressedButton() },
                    shape = RoundedCornerShape( 4.dp ),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant,
                        contentColor = MaterialTheme.colorScheme.background
                    )
                ) { Text( textButton ) }
            }
        }
    }
}