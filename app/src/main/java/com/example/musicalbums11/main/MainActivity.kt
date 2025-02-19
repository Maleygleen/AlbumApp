package com.example.musicalbums11.main

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.musicalbums11.AlbumAdapter
import com.example.musicalbums11.ApiConverter.ApiConverter
import com.example.musicalbums11.DetailActivity
import com.example.musicalbums11.databinding.ActivityMainBinding
import com.example.musicalbums11.model.Album
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val albumAdapter by lazy {
        AlbumAdapter(emptyList()) { album -> openDetailActivity(album) }
    }
    private val apiService = ApiConverter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.recyclerView.layoutManager = GridLayoutManager(this, 2)
        binding.recyclerView.adapter = albumAdapter

        loadAlbums()

        supportActionBar?.hide()
    }

    private fun loadAlbums() {
        lifecycleScope.launch {
            try {
                val albums = apiService.fetchAlbums("https://rss.applemarketingtools.com/api/v2/us/music/most-played/100/albums.json")
                albumAdapter.updateAlbums(albums)
            } catch (e: Exception) {
                Toast.makeText(this@MainActivity, "Ошибка: ${e.localizedMessage}", Toast.LENGTH_SHORT).show()
            }
        }
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

    override fun onDestroy() {
        super.onDestroy()
        apiService.close()
    }
}
