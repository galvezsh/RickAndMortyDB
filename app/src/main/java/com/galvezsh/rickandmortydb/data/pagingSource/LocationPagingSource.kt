package com.galvezsh.rickandmortydb.data.pagingSource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.galvezsh.rickandmortydb.data.remote.api.RetrofitApiService
import com.galvezsh.rickandmortydb.data.remote.model.resultResponse.LocationResponse
import com.galvezsh.rickandmortydb.domain.model.LocationModel
import com.galvezsh.rickandmortydb.data.remote.model.toDomain
import okio.IOException

class LocationPagingSource(
    private val api: RetrofitApiService,
    private val name: String,
    private val type: String,
    private val onCountExtracted: (Int) -> Unit
): PagingSource<Int, LocationModel>() {

    override fun getRefreshKey( state: PagingState<Int, LocationModel> ): Int? {
        return state.anchorPosition
    }

    override suspend fun load( params: LoadParams<Int> ): LoadResult<Int, LocationModel> {
        return try {
            val page = params.key ?: 1
            val response = api.getLocations( page, name, type )

            if ( response.isSuccessful ) {
                val body = response.body()
                val locations = body?.results ?: emptyList<LocationResponse>()
                onCountExtracted( body?.info?.count ?: 0 )

                val prevKey = if ( body?.info?.prev != null ) page-1 else null
                val nextKey = if ( body?.info?.next != null ) page+1 else null

                // Ok
                LoadResult.Page(
                    data = locations.map { it.toDomain() },
                    prevKey = prevKey,
                    nextKey = nextKey
                )

                // No data
            } else {
                onCountExtracted( 0 )
                LoadResult.Page(
                    data = emptyList<LocationModel>(),
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