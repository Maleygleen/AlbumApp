package com.example.musicalbums11.main

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.musicalbums11.AlbumAdapter
import com.example.musicalbums11.ApiConverter.ApiConverter
import com.example.musicalbums11.R
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    // TODO: use ViewBinding
    private lateinit var recyclerView: RecyclerView
    // TODO: don't use lateinit in your code, better use 'by lazy' delegate
    private lateinit var albumAdapter: AlbumAdapter
    private val apiService = ApiConverter()

    // TODO: useless annotation
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        albumAdapter = AlbumAdapter(emptyList())
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = albumAdapter
        recyclerView.layoutManager = GridLayoutManager(this, 2)

        lifecycleScope.launch {
            try {
                val albums = apiService.fetchAlbums("https://rss.applemarketingtools.com/api/v2/us/music/most-played/100/albums.json")
                albumAdapter.updateAlbums(albums)
            } catch (e: Exception) {
                Toast.makeText(this@MainActivity, "Ошибка: ${e.localizedMessage}", Toast.LENGTH_SHORT).show()
            }
        }
        // TODO: the most important part of Android UI is Theme and Styles.
        //  You can delete this line and replace your current theme with Theme.MaterialComponents.DayNight.NoActionBar
        //  /res/values/themes.xml and /res/values-night/themes.xml
        //  https://medium.com/@gaurav.khanna/mastering-android-themes-chapter-1-4aadfa750ca7
        supportActionBar?.hide()

    }

    override fun onDestroy() {
        super.onDestroy()
        apiService.close()
    }
}