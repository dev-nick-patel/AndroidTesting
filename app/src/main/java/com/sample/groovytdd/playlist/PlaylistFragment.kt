package com.sample.groovytdd.playlist

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sample.groovytdd.R
import com.sample.groovytdd.databinding.FragmentPlaylistsBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onSubscription
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject


@AndroidEntryPoint
class PlaylistFragment : Fragment() {

    private lateinit var binding: FragmentPlaylistsBinding
    @Inject
    lateinit var viewModel: PlaylistViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPlaylistsBinding.inflate(inflater, container, false)
        val view = binding.root
        viewModel.loader.observe(this as LifecycleOwner, Observer { loading ->
            when(loading){
                true -> binding.loader.visibility = View.VISIBLE
                else -> binding.loader.visibility = View.GONE
            }
        })

        viewModel.playlists.observe(this as LifecycleOwner, Observer {
            if(it.getOrNull() != null)
                setupRecycleList(it.getOrNull()!!)
            else {
                //TODO
            }

        })
        return view
    }

    private fun setupRecycleList(
        it: List<Playlist>
    ) {
        binding.playlistsList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = MyPlaylistRecyclerViewAdapter(it)
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            PlaylistFragment().apply {

            }
    }
}