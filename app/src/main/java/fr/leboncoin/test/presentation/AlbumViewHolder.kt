package fr.leboncoin.test.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import fr.leboncoin.test.R
import fr.leboncoin.test.commun.loadUrl
import fr.leboncoin.test.data.model.Album
import fr.leboncoin.test.databinding.ItemAlbumBinding

class AlbumViewHolder(private val binding: ItemAlbumBinding) : RecyclerView.ViewHolder(binding.root) {

    companion object {
        fun from(parent: ViewGroup): RecyclerView.ViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ItemAlbumBinding.inflate(layoutInflater, parent, false)
            return AlbumViewHolder(binding)
        }
    }

    fun bind(album: Album) {
        binding.apply {
            title.text = album.title
            thumbnail.loadUrl(album.url, R.drawable.ic_default)
        }
    }
}