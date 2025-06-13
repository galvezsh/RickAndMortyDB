package com.galvezsh.rickandmortydb.di

import com.galvezsh.rickandmortydb.data.RetrofitApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * This NetworkModule defines how to provide instances of OkHttpClient, Retrofit,
 * and our RetrofitApiService using Dagger Hilt.
 *
 * Each function annotated with @Provides tells Hilt how to construct a specific type.
 *
 * - Hilt first sees `provideOkHttpClient()` and knows how to create an OkHttpClient.
 * - Then it sees `provideRetrofit(okHttpClient)` which needs an OkHttpClient.
 *   Hilt automatically injects the one it already knows how to build.
 * - Finally, `provideApiService(retrofit)` requires a Retrofit instance,
 *   which Hilt also provides by chaining the previous functions.
 *
 * This automatic dependency resolution allows us to request `RetrofitApiService`
 * anywhere in the app, and Hilt will resolve and inject all its dependencies.
 */
@Module
@InstallIn( SingletonComponent::class )
object NetworkModule {

    private const val BASE_URL = "https://rickandmortyapi.com/"

    @Provides
    fun provideOkHttpClient(): OkHttpClient = OkHttpClient.Builder().build()

    @Provides
    fun provideRetrofit( okHttpClient: OkHttpClient ): Retrofit =
        Retrofit
            .Builder()
            .baseUrl( BASE_URL )
            .addConverterFactory( GsonConverterFactory.create() )
            .client( okHttpClient )
            .build()

    @Provides
    fun provideApiService( retrofit: Retrofit ): RetrofitApiService = retrofit.create( RetrofitApiService::class.java )

}