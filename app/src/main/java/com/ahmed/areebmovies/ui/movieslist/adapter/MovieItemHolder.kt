package com.ahmed.areebmovies.ui.movieslist.adapter

import com.ahmed.areebmovies.R
import com.ahmed.areebmovies.data.models.dto.Movie
import com.ahmed.areebmovies.databinding.ItemMovieBinding
import com.ahmed.areebmovies.ui.base.BaseViewHolder
import com.ahmed.areebmovies.ui.base.ListItemClickListener
import com.ahmed.areebmovies.utils.DateTimeHelper
import com.ahmed.areebmovies.utils.Utils.roundTheNumber
import com.ahmed.areebmovies.utils.setNetworkImage
import com.ahmed.areebmovies.utils.alternate

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
            binding.txtVote.context.getString(R.string.rate, roundTheNumber(voteAverage))
    }

    private fun bindMovieIcon(posterPath: String?) {
        binding.imgMovie.setNetworkImage(posterPath)
    }
}