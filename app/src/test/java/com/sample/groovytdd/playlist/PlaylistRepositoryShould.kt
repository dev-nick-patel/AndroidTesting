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
    private val playlists = mock<List<Playlist>>()
    private val exception = RuntimeException("Something went wrong")

    @Test
    fun getPlaylistFromService(): Unit = runBlocking{

        val repository = PlaylistRepository(service)

        repository.getPlaylists()

        verify(service, times(1)).fetchPlaylists()

    }

    @Test
    fun emitPlaylistsFromService(): Unit = runBlocking {

        val repository = mockSuccessfulCase()
        assertEquals(playlists, repository.getPlaylists().first().getOrNull())
    }

    @Test
    fun propagateErrors() : Unit = runBlocking{
        val repository = mockFailureCase()
        assertEquals(exception, repository.getPlaylists().first().exceptionOrNull())
    }

    private suspend fun mockSuccessfulCase(): PlaylistRepository {
        whenever(service.fetchPlaylists()).thenReturn(
            flow {
                emit(Result.success(playlists))
            }
        )
        return PlaylistRepository(service)
    }

    private suspend fun mockFailureCase(): PlaylistRepository {
        whenever(service.fetchPlaylists()).thenReturn(
            flow {
                emit(Result.failure<List<Playlist>>(exception))
            }
        )

        val repository = PlaylistRepository(service)
        return repository
    }

}