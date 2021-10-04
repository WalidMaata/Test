package fr.leboncoin.test.data.source.local

import fr.leboncoin.test.data.model.Album
import fr.leboncoin.test.data.source.AlbumDataSource
import kotlinx.coroutines.flow.Flow


class AlbumLocalDataSource internal constructor(
    private val albumDao: AlbumDao
) : AlbumDataSource{

    override fun observeAlbums() : Flow<List<Album>> = albumDao.getAlbums()

    override suspend fun insertAlbums(albums: List<Album>) = albumDao.insertAlbums(albums)

    override suspend fun fetchRemoteAlbums() : List<Album> = listOf()
}