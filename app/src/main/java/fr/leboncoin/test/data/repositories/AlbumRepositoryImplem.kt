package fr.leboncoin.test.data.repositories

import fr.leboncoin.test.data.Result
import fr.leboncoin.test.data.model.Album
import fr.leboncoin.test.data.source.AlbumDataSource
import fr.leboncoin.test.data.source.local.AlbumDao
import fr.leboncoin.test.data.source.local.AlbumLocalDataSource
import fr.leboncoin.test.data.source.remote.AlbumRemoteDataSource
import fr.leboncoin.test.data.source.remote.ApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AlbumRepositoryImplem(
    private val albumLocalDataSource: AlbumDataSource,
    private val albumRemoteDataSource: AlbumDataSource
) : AlbumRepository {


    override fun observeAlbums(): Flow<Result<List<Album>>> {
        return albumLocalDataSource.observeAlbums().map {
            Result.Success(it)
        }
    }

    override suspend fun fetchAlbums() {
        try {
            val albums = albumRemoteDataSource.fetchRemoteAlbums()
            albumLocalDataSource.insertAlbums(albums)
        } catch (exception: Exception) {
            exception.printStackTrace()
        }
    }
}