package fr.leboncoin.test.domain

import fr.leboncoin.test.data.repositories.AlbumRepository
import fr.leboncoin.test.data.Result
import fr.leboncoin.test.data.model.Album
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveAlbumsUseCase @Inject constructor(
    private val albumRepository: AlbumRepository
) {
    operator fun invoke() : Flow<Result<List<Album>>> = albumRepository.observeAlbums()
}