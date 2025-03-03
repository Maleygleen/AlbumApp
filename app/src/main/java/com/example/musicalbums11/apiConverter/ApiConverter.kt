package com.example.musicalbums11.apiConverter

import com.example.musicalbums11.model.Album
import com.example.musicalbums11.model.AlbumResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class ApiConverter {
    val client = HttpClient(CIO) {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
            })
        }
    }

    suspend fun fetchAlbums(url: String): List<Album> {
        val albumResponse: AlbumResponse = client.get(url).body()
        return albumResponse.feed.results
    }

    fun close() {
        client.close()
    }
}