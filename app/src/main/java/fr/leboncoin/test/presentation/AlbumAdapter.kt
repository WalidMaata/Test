package fr.leboncoin.test.presentation

import android.view.ViewGroup
import androidx.recyclerview.widget.*
import fr.leboncoin.test.data.model.Album

class AlbumAdapter : ListAdapter<Album, RecyclerView.ViewHolder>(ArticleDiffCallback()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder = AlbumViewHolder.from(parent)

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is AlbumViewHolder) {
            holder.bind(getItem(position))
        }
    }


    private class ArticleDiffCallback : DiffUtil.ItemCallback<Album>() {
        override fun areItemsTheSame(oldItem: Album, newItem: Album): Boolean = oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Album, newItem: Album): Boolean = oldItem == newItem
    }

}