package com.galvezsh.rickandmortydb.presentation

import android.content.Context
import android.widget.Toast

fun extractIdFromUrl( url: String ) = url.substringAfterLast("/").toIntOrNull()

fun showToast( context: Context, text: String, isLengthShort: Boolean ) {
    Toast.makeText( context, text, if ( isLengthShort ) Toast.LENGTH_SHORT else Toast.LENGTH_LONG ).show()
}

fun parseEpisodeCode( code: String ): Pair<Int, Int>? {
    val regex = Regex("""S(\d{2})E(\d{2})""", RegexOption.IGNORE_CASE)
    val matchResult = regex.find(code)

    return matchResult?.destructured?.let { (season, episode) ->
        Pair(season.toInt(), episode.toInt())
    }
}
