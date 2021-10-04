package fr.leboncoin.test.domain

import fr.leboncoin.test.data.repositories.AlbumRepository
import javax.inject.Inject

class FetchAlbumsUseCase @Inject constructor(
    private val albumRepository: AlbumRepository
) {
    suspend operator fun invoke() = albumRepository.fetchAlbums()
}