package com.ahmed.areebmovies.ui.movieslist.adapter

import com.ahmed.areebmovies.R
import com.ahmed.areebmovies.data.models.dto.Movie
import com.ahmed.areebmovies.databinding.ItemMovieBinding
import com.ahmed.areebmovies.ui.base.BaseViewHolder
import com.ahmed.areebmovies.ui.base.ListItemClickListener
import com.ahmed.areebmovies.utils.Constants.URL.getImageUrl
import com.ahmed.areebmovies.utils.DateTimeHelper
import com.ahmed.areebmovies.utils.alternate
import com.bumptech.glide.Glide

class MovieItemHolder(
    private val binding: ItemMovieBinding,
    private val mMatchItemClickListener: ListItemClickListener<Movie>? = null
) : BaseViewHolder<Movie>(binding, mMatchItemClickListener) {
    override fun bind(item: Movie) {
        bindMovieTitle(item.title)
        bindMovieDate(item.releaseDate)
        bindVoteText(item.voteAverage)
        bindMovieIcon(item.posterPath)
        bindItemClick(item)
    }

    private fun bindItemClick(item: Movie) {
        itemView.setOnClickListener { mMatchItemClickListener?.onItemClick(item, adapterPosition) }
    }

    private fun bindMovieTitle(title: String?) {
        binding.txtTitle.text = title.alternate()
    }

    private fun bindMovieDate(releaseDate: String?) {
        binding.txtDate.text = DateTimeHelper.convertDateStringToAnotherFormat(releaseDate)
    }

    private fun bindVoteText(voteAverage: Double?) {
        binding.txtVote.text =
            binding.txtVote.context.getString(R.string.vote, voteAverage.toString().alternate())
    }

    private fun bindMovieIcon(posterPath: String?) {
        Glide
            .with(binding.root.context)
            .load(getImageUrl(posterPath))
            .fitCenter()
            .placeholder(R.drawable.ic_downloading)
            .error(R.drawable.ic_error)
            .into(binding.imgMovie)
    }
}