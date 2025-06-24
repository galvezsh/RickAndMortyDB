package com.galvezsh.rickandmortydb.data.pagingSource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.galvezsh.rickandmortydb.data.remote.api.RetrofitApiService
import com.galvezsh.rickandmortydb.data.remote.model.resultResponse.EpisodeResponse
import com.galvezsh.rickandmortydb.domain.model.EpisodeModel
import com.galvezsh.rickandmortydb.mappers.toDomain
import okio.IOException

class EpisodePagingSource(
    private val api: RetrofitApiService,
    private val name: String,
    private val season: String,
    private val onCountExtracted: (Int) -> Unit
): PagingSource<Int, EpisodeModel>() {

    override fun getRefreshKey( state: PagingState<Int, EpisodeModel> ): Int? {
        return state.anchorPosition
    }

    override suspend fun load( params: LoadParams<Int> ): LoadResult<Int, EpisodeModel> {
        return try {
            val page = params.key ?: 1
            val response = api.getEpisodes( page, name, season )

            if ( response.isSuccessful ) {
                val body = response.body()
                val episodes = body?.results ?: emptyList<EpisodeResponse>()
                onCountExtracted( body?.info?.count ?: 0 )

                val prevKey = if ( body?.info?.prev != null ) page-1 else null
                val nextKey = if ( body?.info?.next != null ) page+1 else null

                // Ok
                LoadResult.Page(
                    data = episodes.map { it.toDomain() },
                    prevKey = prevKey,
                    nextKey = nextKey
                )

                // No data
            } else {
                onCountExtracted( 0 )
                LoadResult.Page(
                    data = emptyList<EpisodeModel>(),
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