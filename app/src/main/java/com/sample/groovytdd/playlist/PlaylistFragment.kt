package com.sample.groovytdd.playlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sample.groovytdd.R
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class PlaylistFragment : Fragment() {


    lateinit var viewModel: PlaylistViewModel
    lateinit var viewModelFactory: PlaylistViewModelFactory
    private val retrofit = Retrofit.Builder()
        .baseUrl("http://192.168.1.102:3002/") // Local IP
        .client(OkHttpClient())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val api = retrofit.create(PlaylistAPI::class.java)
    private val service = PlaylistService(api)
    private val repository = PlaylistRepository(service)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_playlists, container, false)

        setupViewModel()

        viewModel.playlists.observe(this as LifecycleOwner, Observer {
            if(it.getOrNull() != null)
                setupRecycleList(view, it.getOrNull()!!)
            else {
                //TODO
            }

        })

        return view
    }

    private fun setupRecycleList(
        view: View?,
        it: List<Playlist>
    ) {
        with(view as RecyclerView) {
            layoutManager = LinearLayoutManager(context)
            adapter = MyPlaylistRecyclerViewAdapter(it)
        }
    }

    private fun setupViewModel() {
        viewModelFactory = PlaylistViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(PlaylistViewModel::class.java)
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            PlaylistFragment().apply {

            }
    }
}