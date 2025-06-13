package com.galvezsh.rickandmortydb.data.pagingSource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.galvezsh.rickandmortydb.data.RetrofitApiService
import com.galvezsh.rickandmortydb.data.retrofitResponse.CharacterResponse
import com.galvezsh.rickandmortydb.domain.model.CharacterModel
import okio.IOException

// This is a special class for handling the paging in LazyColumns, returning a constant flow data of CharacterModel
class CharacterPagingSource(
    private val api: RetrofitApiService,
    private val name: String,
    private val gender: String,
    private val status: String,
    private val onCountExtracted: (Int) -> Unit
): PagingSource<Int, CharacterModel>() {

    override fun getRefreshKey( state: PagingState<Int, CharacterModel> ): Int? {
        return state.anchorPosition
    }

    // This is the main function that handles the paging flow
    override suspend fun load( params: LoadParams<Int> ): LoadResult<Int, CharacterModel> {
        return try {
            val page = params.key ?: 1
            val response = api.getCharacters( page, name, gender, status )

            if ( response.isSuccessful ) {
                val body = response.body()
                val characters = body?.results ?: emptyList<CharacterResponse>()
                onCountExtracted( body?.info?.count ?: 0 )

                val prevKey = if ( body?.info?.prev != null ) page-1 else null
                val nextKey = if ( body?.info?.next != null ) page+1 else null

                // Ok
                LoadResult.Page(
                    data = characters.map { character -> character.toMap() },
                    prevKey = prevKey,
                    nextKey = nextKey
                )

                // No data
            } else {
                onCountExtracted( 0 )
                LoadResult.Page(
                    data = emptyList<CharacterModel>(),
                    prevKey = null,
                    nextKey = null
                )
            }

            // No internet catch
        } catch ( exception: IOException ) {
            LoadResult.Error( exception )
        }
    }
}