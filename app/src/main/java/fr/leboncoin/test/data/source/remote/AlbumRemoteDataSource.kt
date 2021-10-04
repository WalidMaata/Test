package fr.leboncoin.test.data.source.remote

import fr.leboncoin.test.data.model.Album
import fr.leboncoin.test.data.source.AlbumDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class AlbumRemoteDataSource internal constructor(
    private val apiService: ApiService
) : AlbumDataSource {

    override suspend fun fetchRemoteAlbums(): List<Album> {
       val result = apiService.getAlbums()
       return result
    }

    override suspend fun insertAlbums(albums: List<Album>) {
        //ignore
    }

    override fun observeAlbums(): Flow<List<Album>> {
        return MutableStateFlow(listOf())
    }
}