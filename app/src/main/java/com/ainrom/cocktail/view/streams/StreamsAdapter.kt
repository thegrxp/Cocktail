package com.ainrom.cocktail.view.streams

import androidx.recyclerview.widget.DiffUtil
import com.ainrom.cocktail.R
import com.ainrom.cocktail.data.Stream
import com.ainrom.cocktail.view.databinding.DataBindingAdapter

class StreamsAdapter(override val viewModel: StreamsViewModel) :
    DataBindingAdapter<Stream, StreamsViewModel>(object : DiffUtil.ItemCallback<Stream>() {
        override fun areItemsTheSame(oldItem: Stream, newItem: Stream): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: Stream, newItem: Stream): Boolean {
            return oldItem.name == newItem.name
        }
    }, viewModel) {

    override fun getItemViewType(position: Int): Int = R.layout.item_stream
}