package fr.leboncoin.test.data.source

import fr.leboncoin.test.data.model.Album
import kotlinx.coroutines.flow.Flow

interface AlbumDataSource {

    fun observeAlbums(): Flow<List<Album>>
    suspend fun insertAlbums(albums: List<Album>)
    suspend fun fetchRemoteAlbums(): List<Album>
}