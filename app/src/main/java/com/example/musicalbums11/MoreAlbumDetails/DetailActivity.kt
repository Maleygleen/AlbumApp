// TODO: code style error - Kotlin Essentials
package com.example.musicalbums11

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import coil.load

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        // TODO: use ViewBinding
        val albumName = intent.getStringExtra("ALBUM_NAME")
        val artistName = intent.getStringExtra("ARTIST_NAME")
        val genre = intent.getStringExtra("GENRE") ?: "Неизвестный жанр"
        val releaseDate = intent.getStringExtra("RELEASE_DATE")
        val copyright = intent.getStringExtra("COPYRIGHT")
        val albumCoverUrl = intent.getStringExtra("ALBUM_COVER")
        val albumUrl = intent.getStringExtra("ALBUM_URL")

        val albumCoverImageView: ImageView = findViewById(R.id.detailAlbumCover)
        val albumNameTextView: TextView = findViewById(R.id.detailAlbumName)
        val artistNameTextView: TextView = findViewById(R.id.detailArtistName)
        val genreTextView: TextView = findViewById(R.id.detailGenre)
        val releaseDateTextView: TextView = findViewById(R.id.detailReleaseDate)
        val copyrightTextView: TextView = findViewById(R.id.detailCopyright)
        val btnOpenITunes: Button = findViewById(R.id.btnOpenITunes)

        albumCoverImageView.load(albumCoverUrl) {
            crossfade(true)
        }
        albumNameTextView.text = albumName
        artistNameTextView.text = artistName
        // TODO: use string resources with parameters, it is better for further app localization
        genreTextView.text = "Жанр: $genre"
        releaseDateTextView.text = "Дата выпуска: $releaseDate"
        copyrightTextView.text = "© $copyright"

        btnOpenITunes.setOnClickListener {
            albumUrl?.let { url ->
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                startActivity(intent)
            }
        }
        // TODO: the most important part of Android UI is Theme and Styles.
        //  You can delete this line and replace your current theme with Theme.MaterialComponents.DayNight.NoActionBar
        //  /res/values/themes.xml and /res/values-night/themes.xml
        //  https://medium.com/@gaurav.khanna/mastering-android-themes-chapter-1-4aadfa750ca7
        supportActionBar?.hide()

    }
}
