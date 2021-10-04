package fr.leboncoin.test.data.source.remote

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import fr.leboncoin.test.MainCoroutineRule
import fr.leboncoin.test.data.model.Album
import fr.leboncoin.test.enqueueResponse
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockWebServer
import org.junit.*
import org.junit.runner.RunWith
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Integration test for [AlbumRemoteDataSource]
 */
@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@MediumTest
class AlbumRemoteDataSourceTest {

    //class under test
    private lateinit var albumRemoteDataSource: AlbumRemoteDataSource
    private val mockWebServer = MockWebServer()

    // Set the main coroutines dispatcher for unit testing.
    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        val apiService = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)

        albumRemoteDataSource = AlbumRemoteDataSource(apiService)
    }

    @Test
    fun fetchRemoteAlbumSucceed() {
        //given
        mockWebServer.enqueueResponse("album.json", 200)

        runBlocking {
            val actual = albumRemoteDataSource.fetchRemoteAlbums()

            val expected = listOf(
                Album(
                    id = 1,
                    title = "first album title",
                    url = "https://via.placeholder.com/600/92c952",
                    thumbnailUrl = "https://via.placeholder.com/150/92c952"
                ),

                Album(
                    id = 2,
                    title = "second album title",
                    url = "https://via.placeholder.com/600/771796",
                    thumbnailUrl = "https://via.placeholder.com/150/771796"
                )
            )

            assertEquals(actual.size, expected.size)
            assertEquals(actual.size, 2)
            assertEquals(expected.size, 2)
            assertEquals(actual[0].id, expected[0].id)
            assertEquals(actual[1].id, expected[1].id)
            assertEquals(actual[0].title, expected[0].title)
            assertEquals(actual[1].title, expected[1].title)
        }
    }

    @After
    fun cleanUp() {
        mockWebServer.shutdown()
    }

}