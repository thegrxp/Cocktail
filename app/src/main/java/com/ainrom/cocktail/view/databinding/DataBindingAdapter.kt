package com.ainrom.cocktail.view.databinding

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter

abstract class DataBindingAdapter<T, V>(diffCallback: DiffUtil.ItemCallback<T>, open val viewModel: V) :
    ListAdapter<T, DataBindingViewHolder<T, V>>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataBindingViewHolder<T, V> {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ViewDataBinding>(layoutInflater, viewType, parent, false)
        return DataBindingViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DataBindingViewHolder<T, V>, position: Int) {
        holder.bind(getItem(position), viewModel)
    }

    abstract override fun getItemViewType(position: Int): Int
}