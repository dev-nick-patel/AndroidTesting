package com.sample.groovytdd.playlist

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.sample.groovytdd.utils.BaseUnitTest
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Test
import java.lang.RuntimeException

class PlaylistRepositoryShould : BaseUnitTest(){

    private val service: PlaylistService = mock()
    private val mapper: PlaylistMapper = mock()
    private val playlists = mock<List<Playlist>>()
    private val playlistsRaw = mock<List<PlaylistRaw>>()
    private val exception = RuntimeException("Something went wrong")

    @Test
    fun getPlaylistFromService(): Unit = runBlocking{

        val repository = PlaylistRepository(service, mapper)

        repository.getPlaylists()

        verify(service, times(1)).fetchPlaylists()

    }

    @Test
    fun emitMappedPlaylistsFromService(): Unit = runBlocking {

        val repository = mockSuccessfulCase()
        assertEquals(playlists, repository.getPlaylists().first().getOrNull())
    }

    @Test
    fun propagateErrors() : Unit = runBlocking{
        val repository = mockFailureCase()
        assertEquals(exception, repository.getPlaylists().first().exceptionOrNull())
    }

    // Playlist Mapper
    @Test
    fun delegateBusinessLogicToMapper(): Unit = runBlocking{
        val repository = mockSuccessfulCase()

        repository.getPlaylists().first()
        verify(mapper, times(1)).invoke(playlistsRaw)

    }



    private suspend fun mockSuccessfulCase(): PlaylistRepository {
        whenever(service.fetchPlaylists()).thenReturn(
            flow {
                emit(Result.success(playlistsRaw))
            }
        )
        whenever(mapper.invoke(playlistsRaw)).thenReturn(playlists)
        return PlaylistRepository(service, mapper)
    }

    private suspend fun mockFailureCase(): PlaylistRepository {
        whenever(service.fetchPlaylists()).thenReturn(
            flow {
                emit(Result.failure<List<PlaylistRaw>>(exception))
            }
        )

        return PlaylistRepository(service, mapper)
    }

}