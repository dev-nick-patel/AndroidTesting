package com.sample.groovytdd.di

import com.jakewharton.espresso.OkHttp3IdlingResource
import com.sample.groovytdd.playlist.PlaylistAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val client = OkHttpClient()
val idlingResource = OkHttp3IdlingResource.create("okhttp", client)


@Module
@InstallIn(FragmentComponent::class)
class PlaylistModule {

    @Provides
    fun playlistAPI(retrofit: Retrofit) : PlaylistAPI {
        return retrofit.create(PlaylistAPI::class.java)
    }

    @Provides
    fun retrofit() : Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://192.168.1.102:3002/") // Local IP
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

}