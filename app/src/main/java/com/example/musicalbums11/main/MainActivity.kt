package com.example.musicalbums11.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.musicalbums11.AlbumAdapter
import com.example.musicalbums11.ApiConverter.ApiConverter
import com.example.musicalbums11.R
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var albumAdapter: AlbumAdapter
    private val apiService = ApiConverter()

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        albumAdapter = AlbumAdapter(emptyList())
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = albumAdapter

        lifecycleScope.launch {
            try {
                val albums = apiService.fetchAlbums("https://rss.applemarketingtools.com/api/v2/us/music/most-played/100/albums.json")
                albumAdapter.updateAlbums(albums)
            } catch (e: Exception) {
                Toast.makeText(this@MainActivity, "Ошибка: ${e.localizedMessage}", Toast.LENGTH_SHORT).show()
            }
        }
        supportActionBar?.hide()

    }

    override fun onDestroy() {
        super.onDestroy()
        apiService.close()
    }
}