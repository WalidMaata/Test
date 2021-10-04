package fr.leboncoin.test.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import fr.leboncoin.test.MainCoroutineRule
import fr.leboncoin.test.data.succeeded
import fr.leboncoin.test.domain.*
import fr.leboncoin.test.presentation.MainViewModel
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.*

@ExperimentalCoroutinesApi
class MainViewModelTest {

    //class under test
    private lateinit var mainViewModel: MainViewModel

    //use fake use cases to be injected into viewModel
    private val fetchAlbumsUseCase = FetchAlbumsUseCase(FakeAlbumRepository())
    private val observeAlbumsUseCase = ObserveAlbumsUseCase(FakeAlbumRepository())

    // Set the main coroutines dispatcher for unit testing.
    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        mainViewModel = MainViewModel(fetchAlbumsUseCase, observeAlbumsUseCase)
    }

    @Test
    fun fetchAlbumsAndCheckObservableAlbums() = mainCoroutineRule.runBlockingTest {

        //given

        //when
        val result = mainViewModel.albums.value

        //then
        assertEquals(result.succeeded, true)
    }
}