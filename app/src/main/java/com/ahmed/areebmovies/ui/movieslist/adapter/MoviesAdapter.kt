package com.ahmed.areebmovies.ui.movieslist.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.ahmed.areebmovies.data.models.dto.Movie
import com.ahmed.areebmovies.databinding.ItemMovieBinding
import com.ahmed.areebmovies.ui.base.BaseAdapter
import com.ahmed.areebmovies.ui.base.ListItemClickListener

class MoviesAdapter(
    private val mContext: Context,
    private val mMovieItemClickListener: ListItemClickListener<Movie>
) : BaseAdapter<Movie, MovieItemHolder>(itemClickListener = mMovieItemClickListener) {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): MovieItemHolder {
        binding = ItemMovieBinding.inflate(
            LayoutInflater.from(mContext),
            viewGroup,
            false
        )
        return MovieItemHolder(
            binding as ItemMovieBinding,
            mMovieItemClickListener,
        )
    }

    override fun clearViewBinding() {
        binding = null
    }
}