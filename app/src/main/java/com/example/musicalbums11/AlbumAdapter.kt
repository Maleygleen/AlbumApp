package com.example.musicalbums11

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.musicalbums11.databinding.ItemAlbumBinding
import com.example.musicalbums11.model.Album

// TODO: use ListAdapter instead of RecyclerView.Adapter
//   https://medium.com/geekculture/android-listadapter-a-better-implementation-for-the-recyclerview-1af1826a7d21
//   https://www.thedroidsonroids.com/blog/difference-between-listview-recyclerview
class AlbumAdapter(private var albums: List<Album>,private val onItemClick: (Album) -> Unit) : RecyclerView.Adapter<AlbumAdapter.AlbumViewHolder>() {

    class AlbumViewHolder(private val binding: ItemAlbumBinding) : RecyclerView.ViewHolder(binding.root) {
        // TODO: use 'private' modifier where is possible
        // TODO: use ViewBinding instead of findViewById

        fun bind(album: Album) {
            binding.albumName.text = album.name
            binding.albumAuthor.text = album.artistName
            binding.albumCover.load(album.artworkUrl100.replace("100x100","1920x1080")) {
                crossfade(true)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        val binding = ItemAlbumBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return AlbumViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        val album = albums[position]
        holder.bind(album)

        holder.itemView.setOnClickListener {
            // TODO: modify your code like this:
            //  "class AlbumAdapter(private val onItemClick: (Album) -> Unit)"
            //  and here write like this: holder.itemView.setOnClickListener { onItemClick(album) }
            //  read more about Kotlin Lambda and Lambda with receiver in Kotlin Essentials
            holder.itemView.setOnClickListener { onItemClick(album) }
        }
    }

    fun updateAlbums(newAlbums: List<Album>) {
        albums = newAlbums
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = albums.size
}
