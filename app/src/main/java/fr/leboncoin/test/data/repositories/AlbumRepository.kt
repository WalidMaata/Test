package fr.leboncoin.test.data.repositories

import fr.leboncoin.test.data.Result
import fr.leboncoin.test.data.model.Album
import kotlinx.coroutines.flow.Flow

interface AlbumRepository {

    fun observeAlbums() : Flow<Result<List<Album>>>

    suspend fun fetchAlbums()

}