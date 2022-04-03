package com.sample.groovytdd

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sample.groovytdd.playlist.PlaylistFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null){

            supportFragmentManager.beginTransaction()
                .add(R.id.containerView, PlaylistFragment.newInstance())
                .commit()
        }
    }
}