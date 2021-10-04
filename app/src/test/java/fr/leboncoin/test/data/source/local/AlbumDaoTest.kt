package fr.leboncoin.test.data.source.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import fr.leboncoin.test.MainCoroutineRule
import fr.leboncoin.test.data.model.Album
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.*
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class AlbumDaoTest {

    private lateinit var database: TestDataBase

    // Set the main coroutines dispatcher for unit testing.
    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun initDb() {
        // using an in-memory database because the information stored here disappears when the
        // process is killed
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            TestDataBase::class.java
        ).allowMainThreadQueries().build()
    }

    @After
    fun closeDb() = database.close()

    @Test
    fun insertAndGetAlbums() = runBlockingTest {
        //given
        val album = Album(
            id = 1,
            title = "title",
            url = "https://fakeurl.com",
            thumbnailUrl = "https://fakeUrlTo.com"
        )
        val albums = listOf(album)
        database.albumDao().insertAlbums(albums = albums)

        //when
        val result = database.albumDao().getAlbums().first()

        //then
        assertThat(result.size, `is`(1))
        assertThat(result[0].id, `is`(album.id))
        assertThat(result[0].title, `is`(album.title))
        assertThat(result[0].url, `is`(album.url))
        assertThat(result[0].thumbnailUrl, `is`(album.thumbnailUrl))
    }

    @Test
    fun insertAlbumReplacesOnConflict() = runBlockingTest {
        //given
        val album = Album(
            id = 1,
            title = "title",
            url = "https://fakeurl.com",
            thumbnailUrl = "https://fakeUrlTo.com"
        )
        database.albumDao().insertAlbums(listOf(album))

        //when
        val newAlbum =  Album(
            id = album.id,
            title = "title2",
            url = "https://newFakeurl.com",
            thumbnailUrl = "https://newFakeUrlTo.com"
        )

        database.albumDao().insertAlbums(listOf(newAlbum))

        //then
        val result = database.albumDao().getAlbums().first()
        assertThat(result.size, `is`(1))
        assertThat(result[0].id, `is`(album.id))
        assertThat(result[0].title, `is`("title2"))
        assertThat(result[0].url, `is`("https://newFakeurl.com"))
        assertThat(result[0].thumbnailUrl, `is`("https://newFakeUrlTo.com"))
    }
}