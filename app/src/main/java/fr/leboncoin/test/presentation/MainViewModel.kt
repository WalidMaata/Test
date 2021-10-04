package fr.leboncoin.test.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import fr.leboncoin.test.data.Result
import fr.leboncoin.test.data.model.Album
import fr.leboncoin.test.domain.FetchAlbumsUseCase
import fr.leboncoin.test.domain.ObserveAlbumsUseCase
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val fetchAlbumsUseCase: FetchAlbumsUseCase,
    private val observeAlbumsUseCase: ObserveAlbumsUseCase
) : ViewModel() {

    private val _albums = MutableStateFlow<Result<List<Album>>>(Result.Loading)
    val albums : StateFlow<Result<List<Album>>> = _albums

    init {
        fetchArticles()
        observeArticles()
    }


    private fun fetchArticles()  = viewModelScope.launch {
        fetchAlbumsUseCase()
    }

    private fun observeArticles() = viewModelScope.launch {
        observeAlbumsUseCase().distinctUntilChanged().collect {
            _albums.value = it
        }
    }

}