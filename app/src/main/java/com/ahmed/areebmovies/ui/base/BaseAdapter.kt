package com.ahmed.areebmovies.ui.base

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class BaseAdapter<Item, ViewHolder : BaseViewHolder<Item>>(
    protected val dataList: ArrayList<Item> = arrayListOf(),
    private val itemClickListener: ListItemClickListener<Item>? = null
) : RecyclerView.Adapter<ViewHolder>() {

    var binding: ViewBinding? = null
    override fun onBindViewHolder(
        viewHolder: ViewHolder,
        position: Int
    ) {
        val item = dataList[position]
        return viewHolder.bind(item)

    }

    override fun getItemCount(): Int = dataList.size

    abstract fun clearViewBinding()

    fun addItems(data: ArrayList<Item>) {
        val positionStart = dataList.size
        dataList.addAll(data)
        notifyItemRangeInserted(positionStart, data.size)
    }

    fun addItem(item: Item, position: Int = dataList.size) {
        dataList.add(item)
        notifyItemInserted(position)
    }

    fun replaceItems(newItems: ArrayList<Item>) {
        dataList.clear()
        dataList.addAll(newItems)
        notifyDataSetChanged()
    }

    fun updateItem(item: Item) {
        val index = dataList.indexOf(item)
        dataList[index] = item
        notifyItemChanged(index)
    }

    fun updateItem(item: Item, index: Int) {
        dataList[index] = item
        notifyItemChanged(index)
    }

    fun deleteItem(item: Item) {
        val indexOf = dataList.indexOf(item)
        if (indexOf != -1) {
            dataList.remove(item)
            notifyItemRemoved(indexOf)
        }
    }

    fun deleteItem(index: Int) {
        if ((dataList.size - 1) >= index) {
            dataList.removeAt(index)
            notifyItemRemoved(index)
        }
    }

    fun clearItems() {
        dataList.clear()
        notifyDataSetChanged()
    }
}