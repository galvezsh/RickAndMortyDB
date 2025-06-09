package com.galvezsh.rickandmortydb.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.FilterAlt
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.galvezsh.rickandmortydb.R

@Composable
fun ShowSpacer( dp: Dp ) {
    Spacer( modifier = Modifier.padding(dp) )
}

@Composable
fun ShowHeader( text: String, onPressedSearch: () -> Unit, onPressedFilter: () -> Unit ) {
    Box( modifier = Modifier.fillMaxWidth() ) {
        Text(
            text = text,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            modifier = Modifier.align( Alignment.Center ),
            color = MaterialTheme.colorScheme.surface
        )

        Row(
            modifier = Modifier.align( Alignment.CenterEnd ),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton( onClick = { onPressedSearch }, modifier = Modifier.size( 32.dp ) ) {
                Icon(
                    imageVector = Icons.Rounded.Search,
                    contentDescription = stringResource( R.string.icon_search ),
                    tint = MaterialTheme.colorScheme.surface
                )
            }
            ShowSpacer( 2.dp )
            IconButton( onClick = { onPressedFilter }, modifier = Modifier.size( 32.dp ) ) {
                Icon(
                    imageVector = Icons.Rounded.FilterAlt,
                    contentDescription = stringResource( R.string.icon_filter ),
                    tint = MaterialTheme.colorScheme.surface
                )
            }
        }
    }
}