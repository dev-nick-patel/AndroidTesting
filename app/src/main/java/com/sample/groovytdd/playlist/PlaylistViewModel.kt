package com.sample.groovytdd.playlist

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class PlaylistViewModel @Inject constructor(
    private val repository: PlaylistRepository
): ViewModel() {

    val loader = MutableLiveData<Boolean>()

    val playlists = liveData<Result<List<Playlist>>> {
        loader.postValue(true)
        emitSource(repository.getPlaylists()
            .onEach {
               loader.postValue(false)
            }
            .asLiveData())
    }

}
