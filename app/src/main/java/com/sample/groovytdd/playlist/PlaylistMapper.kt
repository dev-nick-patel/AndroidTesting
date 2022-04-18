package com.sample.groovytdd.playlist

import android.annotation.SuppressLint
import com.sample.groovytdd.R
import javax.inject.Inject

class PlaylistMapper @Inject constructor(): Function1<List<PlaylistRaw>, List<Playlist>>
{
    @SuppressLint("StopShip")
    override fun invoke(playlistRaw: List<PlaylistRaw>): List<Playlist> {
     return playlistRaw.map {
         val image = when(it.category){
             "rock" -> R.mipmap.rock
             else -> R.mipmap.ic_launcher
         }
         Playlist(it.id,
             it.name,
             it.category,
         image)
     }
    }

}
