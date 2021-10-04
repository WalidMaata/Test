package fr.leboncoin.test.presentation

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.*
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import fr.leboncoin.test.data.Result
import fr.leboncoin.test.databinding.ActivityMainBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding

    private val adapter: AlbumAdapter by lazy {
        AlbumAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.albumRecyclerView.adapter = adapter
        observeAlbums()
    }

    private fun observeAlbums() {
        // Start a coroutine in the lifecycle scope
        lifecycleScope.launch {
            // repeatOnLifecycle launches the block in a new coroutine every time the
            // lifecycle is in the STARTED state (or above) and cancels it when it's STOPPED.
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.albums.collect { state ->
                    when (state) {
                        is Result.Success -> {
                            binding.swipeToRefreshLayout.isRefreshing = state.data.isEmpty()
                            adapter.submitList(state.data)
                        }
                        is Result.Error -> {
                            binding.swipeToRefreshLayout.isRefreshing = true
                            Snackbar.make(binding.root, "Error when load data", Snackbar.LENGTH_SHORT).show()
                        }
                        is Result.Loading -> binding.swipeToRefreshLayout.isRefreshing = true
                    }
                }
            }
        }
    }
}