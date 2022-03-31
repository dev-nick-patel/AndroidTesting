package com.sample.groovytdd

data class Playlist(
    val id: String,
    val name: String,
    val category: String,
    val image: Int = R.drawable.ic__play_24
) {
}