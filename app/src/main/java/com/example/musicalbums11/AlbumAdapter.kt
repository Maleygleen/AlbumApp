package com.example.musicalbums11

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.musicalbums11.databinding.ItemAlbumBinding
import com.example.musicalbums11.model.Album

class AlbumAdapter(private val onItemClick: (Album) -> Unit) :
    ListAdapter<Album, AlbumAdapter.AlbumViewHolder>(AlbumDiffCallback()) {

    class AlbumViewHolder(private val binding: ItemAlbumBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(album: Album, onItemClick: (Album) -> Unit) {
            binding.albumName.text = album.name
            binding.albumAuthor.text = album.artistName
            binding.albumCover.load(album.artworkUrl100.replace("100x100", "1920x1080")) { // TODO: вьюха - тупая и красивая, оператор реплейс тут не должен быть
                crossfade(true)
            }
            binding.root.setOnClickListener { onItemClick(album) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        val binding = ItemAlbumBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AlbumViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        val album = getItem(position)
        holder.bind(album, onItemClick)
    }
}

class AlbumDiffCallback : DiffUtil.ItemCallback<Album>() {
    override fun areItemsTheSame(oldItem: Album, newItem: Album): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Album, newItem: Album): Boolean {
        return oldItem == newItem
    }
}
