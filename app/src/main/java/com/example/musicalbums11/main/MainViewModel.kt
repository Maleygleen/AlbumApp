package com.example.musicalbums11.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.musicalbums11.apiConverter.ApiConverter
import com.example.musicalbums11.model.Album

class MainViewModel : ViewModel() {
    private val apiService = ApiConverter()
    private val _albums = MutableLiveData<List<Album>>()
    val albums: LiveData<List<Album>> get() = _albums

    fun loadAlbums() {
        if (_albums.value != null) return

        viewModelScope.launch {
            try {
                val albumList = apiService.fetchAlbums("https://rss.applemarketingtools.com/api/v2/us/music/most-played/100/albums.json")
                _albums.postValue(albumList)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        apiService.close()
    }
}
