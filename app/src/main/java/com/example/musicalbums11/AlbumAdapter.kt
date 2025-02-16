package com.example.musicalbums11

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.musicalbums11.model.Album

// TODO: use ListAdapter instead of RecyclerView.Adapter
//   https://medium.com/geekculture/android-listadapter-a-better-implementation-for-the-recyclerview-1af1826a7d21
//   https://www.thedroidsonroids.com/blog/difference-between-listview-recyclerview
class AlbumAdapter(private var albums: List<Album>) : RecyclerView.Adapter<AlbumAdapter.AlbumViewHolder>() {

    @SuppressLint("NotifyDataSetChanged")
    fun updateAlbums(newAlbums: List<Album>) {
        albums = newAlbums
        notifyDataSetChanged()
    }

    class AlbumViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // TODO: use 'private' modifier where is possible
        // TODO: use ViewBinding instead of findViewById
        val albumNameTextView: TextView = itemView.findViewById(R.id.albumName)
        val albumCover: ImageView = itemView.findViewById(R.id.albumCover)
        val albumAuthor : TextView = itemView.findViewById(R.id.albumAuthor)

        fun bind(album: Album) {
            albumNameTextView.text = album.name
            albumAuthor.text = album.artistName
            albumCover.load(album.artworkUrl100.replace("100x100","1920x1080")) {
                crossfade(true)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_album, parent, false)
        return AlbumViewHolder(view)
    }

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        val album = albums[position]
        holder.bind(album)

        val genreName = album.genres.firstOrNull()?.name ?: "Неизвестный жанр"

        holder.itemView.setOnClickListener {
            // TODO: modify your code like this:
            //  "class AlbumAdapter(private val onItemClick: (Album) -> Unit)"
            //  and here write like this: holder.itemView.setOnClickListener { onItemClick(album) }
            //  read more about Kotlin Lambda and Lambda with receiver in Kotlin Essentials
            val context = holder.itemView.context
            val intent = Intent(context, DetailActivity::class.java).apply {
                putExtra("ALBUM_NAME", album.name)
                putExtra("ARTIST_NAME", album.artistName)
                putExtra("GENRE", genreName)
                putExtra("RELEASE_DATE", album.releaseDate)
                putExtra("ALBUM_COVER", album.artworkUrl100)
                putExtra("ALBUM_URL", album.url)
            }
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = albums.size
}
