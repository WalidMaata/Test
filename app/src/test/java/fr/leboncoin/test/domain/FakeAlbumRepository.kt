package fr.leboncoin.test.domain

import fr.leboncoin.test.data.Result
import fr.leboncoin.test.data.model.Album
import fr.leboncoin.test.data.repositories.AlbumRepository
import kotlinx.coroutines.flow.*

class FakeAlbumRepository : AlbumRepository {

    private val album1 = Album(id = 1, title = "title1", url = "https://fakeurl1.com", thumbnailUrl = "https://fakeUrlTo1.com")
    private val album2 = Album(id = 2, title = "title2", url = "https://fakeurl2.com", thumbnailUrl = "https://fakeUrlTo2.com")

    private val albums = mutableListOf<Album>()

    private val _flowAlbums = MutableStateFlow<Result<List<Album>>>(Result.Success(albums))

    override fun observeAlbums(): Flow<Result<List<Album>>> {
        return _flowAlbums
    }

    override suspend fun fetchAlbums() {
        albums.apply {
            add(album1)
            add(album2)
        }
        _flowAlbums.value = Result.Success(albums)
    }
}