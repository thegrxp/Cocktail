package com.ainrom.cocktail.view.databinding

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.Glide
import android.widget.ImageView

@Suppress("UNCHECKED_CAST")
@BindingAdapter("data")
fun <T, V> setRecyclerViewData(recyclerView: RecyclerView, data: List<T>?) {
    (recyclerView.adapter as DataBindingAdapter<T, V>).submitList(data)
}
@BindingAdapter("image")
fun loadImage(view: ImageView, imageUrl: String) {
    Glide.with(view.context)
        .load(imageUrl)
        .apply(RequestOptions().centerCrop())
        .into(view)
}

