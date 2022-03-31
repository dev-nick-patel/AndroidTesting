package com.sample.groovytdd

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import org.junit.Test

import org.junit.Assert.*
import org.junit.Rule
import petros.efthymiou.groovy.utils.MainCoroutineScopeRule
import petros.efthymiou.groovy.utils.getValueForTest


class PlaylistViewModelShould {

    @get:Rule
    var coroutinesTestRule = MainCoroutineScopeRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val viewModel: PlaylistViewModel
    private val repository: PlaylistRepository = mock()

    init {
        viewModel = PlaylistViewModel(repository)
    }

    @Test
    fun getPlaylistsFromRepository() {
        viewModel.playlists.getValueForTest()

        verify(repository, times(1)).getPlaylists()
    }
}