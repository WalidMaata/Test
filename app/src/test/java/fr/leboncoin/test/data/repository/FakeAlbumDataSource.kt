package fr.leboncoin.test.data.repository

import fr.leboncoin.test.data.model.Album
import fr.leboncoin.test.data.source.AlbumDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class FakeAlbumDataSource( var albums: MutableList<Album>? = mutableListOf(), val album : Album) : AlbumDataSource {

    override fun observeAlbums(): Flow<List<Album>> {
        albums?.let { return MutableStateFlow(albums!!.toList()) }
        return MutableStateFlow(mutableListOf())
    }

    override suspend fun insertAlbums(albums: List<Album>) {
        this.albums?.addAll(albums)
    }

    override suspend fun fetchRemoteAlbums(): List<Album> = listOf(album)


}