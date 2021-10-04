package fr.leboncoin.test.data.source.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import fr.leboncoin.test.MainCoroutineRule
import fr.leboncoin.test.data.model.Album
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.junit.*
import org.junit.runner.RunWith

/**
 * Integration test for [AlbumLocalDataSource]
 */
@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@MediumTest
class AlbumLocalDataSourceTest {

    //class under test
    private lateinit var albumLocalDataSource: AlbumLocalDataSource
    private lateinit var dataBase: TestDataBase

    // Set the main coroutines dispatcher for unit testing.
    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        dataBase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            TestDataBase::class.java
        )
            .allowMainThreadQueries()
            .build()

        albumLocalDataSource = AlbumLocalDataSource(dataBase.albumDao())
    }

    @After
    fun cleanUp() {
        dataBase.close()
    }

    @Test
    fun insertAlbum_getAlbum() = runBlockingTest {
        //given
        val album = Album(
            id = 1,
            title = "title",
            url = "https://fakeurl.com",
            thumbnailUrl = "https://fakeUrlTo.com"
        )

        //when
        albumLocalDataSource.insertAlbums(listOf(album))
        val result = albumLocalDataSource.observeAlbums().first()

        //then
        assertThat(result.size , `is`(1))
        assertThat(result[0].title , `is`(album.title))
    }
}