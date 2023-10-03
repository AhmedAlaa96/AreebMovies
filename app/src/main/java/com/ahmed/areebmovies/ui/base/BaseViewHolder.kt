package com.ahmed.areebmovies.ui.base

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class BaseViewHolder<Item>(
        binding: ViewBinding,
        private val listItemClickListener: ListItemClickListener<Item>? = null
) : RecyclerView.ViewHolder(binding.root) {


    abstract fun bind(item: Item)
}