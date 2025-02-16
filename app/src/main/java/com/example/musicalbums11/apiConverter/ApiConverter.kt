package com.example.musicalbums11.apiConverter

import com.example.musicalbums11.model.Album
import com.example.musicalbums11.model.Author
import com.example.musicalbums11.model.AlbumResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class ApiConverter {
    var response: AlbumResponse? = null

    val client = HttpClient(CIO) { // TODO: может быть приватным, студия подсвечивает желтым
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
            })
        }
    }

    suspend fun fetchData(): AlbumResponse { // как убрать return в fetchData
        return client.get("https://rss.applemarketingtools.com/api/v2/us/music/most-played/100/albums.json").body();
    }

    suspend fun getAlbums(): List<Album>? {
        return response?.feed?.results
    }

    suspend fun getAuthor (): Author? {
        return response?.feed?.author
    }

    fun close() {
        client.close()
    }
}