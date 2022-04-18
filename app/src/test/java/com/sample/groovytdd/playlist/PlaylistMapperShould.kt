package com.sample.groovytdd.playlist

import com.sample.groovytdd.R
import com.sample.groovytdd.utils.BaseUnitTest
import junit.framework.TestCase.assertEquals
import org.junit.Test

class PlaylistMapperShould : BaseUnitTest() {

    private val playlistRaw = PlaylistRaw("1","da_name","da_category")
    private val playlistRawRock = PlaylistRaw("1","da_name","rock")

    private val mapper = PlaylistMapper()

    private val playlists = mapper(listOf(playlistRaw))

    private val playlist = playlists[0]
    private val playlistRock = mapper(listOf(playlistRawRock))[0]

    @Test
    fun keepSameID(){
        assertEquals(playlistRaw.id, playlist.id)
    }

    @Test
    fun keepSameName() {
        assertEquals(playlistRaw.name, playlist.name)
    }

    @Test
    fun keepSameCategory(){
        assertEquals(playlistRaw.category, playlist.category)
    }

    @Test
    fun mapDefaultImageWhenNotRock(){
        assertEquals(R.mipmap.ic_launcher, playlist.image)
    }

    @Test
    fun mapRockImageWhenRockCategory(){
        assertEquals(R.mipmap.rock, playlistRock.image)
    }
}