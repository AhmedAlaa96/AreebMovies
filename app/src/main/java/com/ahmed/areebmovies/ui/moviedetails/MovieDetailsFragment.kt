package com.ahmed.areebmovies.ui.moviedetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.ahmed.areebmovies.R
import com.ahmed.areebmovies.data.models.LoadingModel
import com.ahmed.areebmovies.data.models.ProgressTypes
import com.ahmed.areebmovies.data.models.StatusCode
import com.ahmed.areebmovies.data.models.dto.MovieDetailsResponse
import com.ahmed.areebmovies.databinding.FragmentMovieDetailsBinding
import com.ahmed.areebmovies.ui.base.BaseFragment
import com.ahmed.areebmovies.utils.*
import com.ahmed.areebmovies.utils.Utils.roundTheNumber
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailsFragment : BaseFragment<FragmentMovieDetailsBinding>() {


    companion object {
        fun newInstance() = MovieDetailsFragment()
    }

    private val viewModel: MovieDetailsViewModel by viewModels()

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentMovieDetailsBinding
        get() = FragmentMovieDetailsBinding::inflate

    override fun onActivityReady(savedInstanceState: Bundle?) {
    }

    override fun initViews() {
    }

    override fun setListeners() {
        binding.btnBack.setOnClickListener {
            navigateUp()
        }
    }

    override fun bindViewModels() {
        val args: MovieDetailsFragmentArgs by navArgs()
        bindLoadingObserver()
        bindErrorObserver()
        bindToastMessageObserver()
        bindGetMovieDetailsObserver()
        viewModel.getMovieDetails(args.movieId)
    }

    private fun bindGetMovieDetailsObserver() {
        observe(viewModel.moviesResponseSharedFlow) {
            when (it.statusCode) {
                StatusCode.SUCCESS -> {
                    binding.scrollView.isVisible = true
                    binding.errorLayout.root.isVisible = false
                    bindMovieDetailsData(it.data!!)
                }
                else -> {
                    binding.scrollView.isVisible = false
                    binding.errorLayout.root.isVisible = true
                    binding.errorLayout.txtError.text =
                        it.error.alternate(getString(R.string.some_thing_went_wrong))
                    binding.errorLayout.btnRetry.isVisible = true
                }
            }
        }

    }

    private fun bindMovieDetailsData(data: MovieDetailsResponse) {
        with(binding) {
            val durationPair = DateTimeHelper.getDurationPair(data.runtime)
            imgMovie.setNetworkImage(data.posterPath)
            txtTitle.text = data.title.alternate()
            txtReleaseDate.text = DateTimeHelper.convertDateStringToAnotherFormat(data.releaseDate)
            txtVote.text = getString(R.string.rate, roundTheNumber(data.voteAverage))
            txtDuration.text = getString(
                R.string.duration, durationPair.first.toString(), durationPair.second.toString()
            )
            txtGenres.text = data.genres?.joinToString(
                separator = "\n",
                prefix = getString(R.string.genres)
            ) { genre ->
                "• ${genre.name}"
            }
            txtSummary.text = data.overview.alternate()
        }
    }


    private fun bindLoadingObserver() {
        observe(viewModel.loadingObservable) {
            onLoadingObserverRetrieved(it)
        }
    }


    private fun onLoadingObserverRetrieved(loadingModel: LoadingModel) {
        loadingModel.loadingProgressView = binding.viewProgress.loadingIndicator
        loadingModel.loadingFullProgressView = binding.viewFullProgress.root
        binding.viewProgress.root.isVisible =
            (loadingModel.shouldShow && loadingModel.progressType == ProgressTypes.MAIN_PROGRESS)

        binding.viewFullProgress.root.isVisible =
            (loadingModel.shouldShow && loadingModel.progressType == ProgressTypes.FULL_PROGRESS)
    }

    private fun bindErrorObserver() {
        observe(viewModel.errorViewObservable) {
            showError(it)
            binding.errorLayout.root.visibility = View.GONE
        }
    }


    private fun bindToastMessageObserver() {
        observe(viewModel.showToastObservable) {
            showMessage(it)
        }
    }


    override fun showError(shouldShow: Boolean) {
    }
}