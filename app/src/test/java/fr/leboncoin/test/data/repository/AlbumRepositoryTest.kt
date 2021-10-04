package fr.leboncoin.test.data.repository

import com.google.common.truth.Truth.assertThat
import fr.leboncoin.test.MainCoroutineRule
import fr.leboncoin.test.data.model.Album
import fr.leboncoin.test.data.repositories.AlbumRepositoryImplem
import fr.leboncoin.test.data.succeeded
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runBlockingTest
import org.junit.*

/**
 * Unit test for AlbumRepositoryImplem
 */
@ExperimentalCoroutinesApi
class AlbumRepositoryTest {

    private lateinit var albumRepository: AlbumRepositoryImplem

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private val album1 = Album(id = 1, title = "title1", url = "https://fakeurl1.com", thumbnailUrl = "https://fakeUrlTo1.com")
    private val album2 = Album(id = 2, title = "title2", url = "https://fakeurl2.com", thumbnailUrl = "https://fakeUrlTo2.com")
    private val newAlbum = Album(id = 3, title = "title3", url = "https://fakeurl3.com", thumbnailUrl = "https://fakeUrlTO3.com")

    private val albums = mutableListOf(album1, album2)

    private val localAlbums = FakeAlbumDataSource(albums, newAlbum)
    private val remoteAlbums = FakeAlbumDataSource(albums, newAlbum)


    @Before
    fun setup() {

        //initialize class under test
        albumRepository = AlbumRepositoryImplem(localAlbums, remoteAlbums)
    }

    @Test
    fun observeAlbumsTest() = mainCoroutineRule.runBlockingTest {
        //given


        //when
        val albums = albumRepository.observeAlbums().first()

        //then
        assertEquals(albums.succeeded, true)
    }

    @Test
    fun fetchAlbumsTest() = mainCoroutineRule.runBlockingTest {
        // Make sure newAlbum is not in the remote or local datasources
        assertThat(localAlbums.albums).doesNotContain(newAlbum)
        assertThat(remoteAlbums.albums).doesNotContain(newAlbum)

        // When a album is saved to the albums repository
        albumRepository.fetchAlbums()

        // Then the remote and local sources are called
        assertThat(localAlbums.albums).contains(newAlbum)
        assertThat(remoteAlbums.albums).contains(newAlbum)
    }
}