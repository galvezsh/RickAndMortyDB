package com.galvezsh.rickandmortydb.presentation

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.FilterAlt
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.galvezsh.rickandmortydb.R

// In this file are defined some composable functions that can be use in multiples screens, like in React,
// because Jetpack Compose works using the same principle, building the interface using components. Thanks to
// this principle, some functions can be recycled, making the code much faster and cleaner

@Composable
fun ShowSpacer( dp: Dp ) {
    Spacer( modifier = Modifier.padding(dp) )
}

@Composable
fun ShowCircularProgressBar() {
    Box( modifier = Modifier.fillMaxSize() ) {
        CircularProgressIndicator( modifier = Modifier.align( Alignment.Center ), color = MaterialTheme.colorScheme.surface )
    }
}

@Composable
fun ShowToast( context: Context, text: String, isLengthShort: Boolean ) {
    Toast.makeText( context, text, if ( isLengthShort ) Toast.LENGTH_SHORT else Toast.LENGTH_LONG ).show()
}

@Composable
fun ShowHeader( text: String, from: Int, to: Int, onPressedSearch: () -> Unit, onPressedFilter: () -> Unit ) {
    Box( modifier = Modifier.fillMaxWidth() ) {
        Text(
            text = "$from " + stringResource( R.string.of ) + " $to",
            fontSize = 16.sp,
            modifier = Modifier.align( Alignment.CenterStart ),
            color = MaterialTheme.colorScheme.surface
        )

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
            IconButton( onClick = { onPressedSearch() }, modifier = Modifier.size( 32.dp ) ) {
                Icon(
                    imageVector = Icons.Rounded.Search,
                    contentDescription = stringResource( R.string.icon_search ),
                    tint = MaterialTheme.colorScheme.surface
                )
            }
            ShowSpacer( 2.dp )
            IconButton( onClick = { onPressedFilter() }, modifier = Modifier.size( 32.dp ) ) {
                Icon(
                    imageVector = Icons.Rounded.FilterAlt,
                    contentDescription = stringResource( R.string.icon_filter ),
                    tint = MaterialTheme.colorScheme.surface
                )
            }
        }
    }
}

@Composable
fun ShowSearchField(text: String, placeholder: String, onTextChanged: (String) -> Unit ) {
    TextField(
        value = text,
        enabled = true,
        onValueChange = { onTextChanged( it ) },
        modifier = Modifier
            .fillMaxWidth()
            .padding( vertical = 8.dp )
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

@Composable
fun ShowErrorBox( text: String ) {
    Box( modifier = Modifier.fillMaxSize().padding( horizontal = 24.dp ) ) {
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
                .padding( vertical = 16.dp )
        )
    }
}

@Composable
fun ShowErrorBoxWithButton( text: String, textButton: String, onPressedButton: () -> Unit ) {
    Box( modifier = Modifier.fillMaxSize().padding( horizontal = 24.dp ) ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .align( Alignment.Center )
                .clip( RoundedCornerShape( 12.dp ) )
                .border( 2.dp, MaterialTheme.colorScheme.surface, RoundedCornerShape( 12.dp ) )
                .background( MaterialTheme.colorScheme.primary )
                .padding( vertical = 16.dp )
                .fillMaxWidth()
        ) {
            Text(
                text = text,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onPrimary
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