package com.example.musicalbums11.main

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.musicalbums11.AlbumAdapter
import com.example.musicalbums11.DetailActivity
import com.example.musicalbums11.databinding.ActivityMainBinding
import com.example.musicalbums11.model.Album

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val albumAdapter by lazy { AlbumAdapter { album -> openDetailActivity(album) } }
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.recyclerView.layoutManager = GridLayoutManager(this, 2)
        binding.recyclerView.adapter = albumAdapter

        observeAlbums()
    }

    private fun observeAlbums() {
        viewModel.albums.observe(this) { albums ->
            albumAdapter.submitList(albums)
        }

        viewModel.loadAlbums()
    }

    private fun openDetailActivity(album: Album) {
        val intent = Intent(this, DetailActivity::class.java).apply {
            putExtra("ALBUM_NAME", album.name)
            putExtra("ARTIST_NAME", album.artistName)
            putExtra("GENRE", album.genres.firstOrNull()?.name ?: "Неизвестный жанр")
            putExtra("RELEASE_DATE", album.releaseDate)
            putExtra("ALBUM_COVER", album.artworkUrl100)
            putExtra("ALBUM_URL", album.url)
        }
        startActivity(intent)
    }
}
