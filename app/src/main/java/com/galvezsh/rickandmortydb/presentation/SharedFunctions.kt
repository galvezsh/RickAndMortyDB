package com.galvezsh.rickandmortydb.presentation

fun extractIdFromUrl( url: String ) = url.substringAfterLast("/").toIntOrNull()
