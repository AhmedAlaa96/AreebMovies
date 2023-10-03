package com.ahmed.areebmovies.ui.movieslist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.ahmed.areebmovies.R
import com.ahmed.areebmovies.data.models.LoadingModel
import com.ahmed.areebmovies.data.models.ProgressTypes
import com.ahmed.areebmovies.data.models.StatusCode
import com.ahmed.areebmovies.data.models.dto.Movie
import com.ahmed.areebmovies.databinding.FragmentMoviesListBinding
import com.ahmed.areebmovies.ui.base.BaseFragment
import com.ahmed.areebmovies.ui.base.ListItemClickListener
import com.ahmed.areebmovies.ui.movieslist.adapter.MoviesAdapter
import com.ahmed.areebmovies.utils.Constants
import com.ahmed.areebmovies.utils.alternate
import com.ahmed.areebmovies.utils.observe
import com.ahmed.areebmovies.utils.utilities.PagingScrollListener
import com.ahmed.areebmovies.utils.view_state.SaveStateLifecycleObserver
import com.ahmed.areebmovies.utils.view_state.ViewStateHelper
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MoviesListFragment : BaseFragment<FragmentMoviesListBinding>(),
    ListItemClickListener<Movie>,
    PagingScrollListener.PagingScrollListenerInteractions {

    companion object {
        fun newInstance() = MoviesListFragment()
    }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentMoviesListBinding
        get() = FragmentMoviesListBinding::inflate

    private val viewModel: MoviesViewModel by viewModels()

    private lateinit var mMoviesLayoutManager: LinearLayoutManager
    private lateinit var moviesAdapter: MoviesAdapter

    override fun onActivityReady(savedInstanceState: Bundle?) {
        initViewStateLifecycle()
    }

    override fun initViews() {
        initAdapter()
    }

    private fun initAdapter() {
        moviesAdapter = MoviesAdapter(mContext, this)
        mMoviesLayoutManager = LinearLayoutManager(mContext)
        binding.rvMoviesList.layoutManager = mMoviesLayoutManager
        binding.rvMoviesList.adapter = moviesAdapter
    }

    override fun setListeners() {
        binding.rvMoviesList.addOnScrollListener(
            PagingScrollListener(mMoviesLayoutManager, this)
        )
        binding.swipeRefreshMovies.setOnRefreshListener {
            onRefresh()
        }

        binding.errorLayout.btnRetry.setOnClickListener {
            onRetryClicked()
        }
    }

    private fun onRetryClicked() {
        viewModel.onRetryClicked()
    }

    private fun onRefresh() {
        viewModel.onRefresh()
    }

    override fun bindViewModels() {
        bindLoadingObserver()
        bindErrorObserver()
        bindToastMessageObserver()
        bindGetMoviesObserver()
        viewModel.getMoviesListResponse()

    }

    private fun initViewStateLifecycle() {
        val viewStateLifecycleObserver =
            SaveStateLifecycleObserver(this::saveState, this::setViewsTags)
        viewLifecycleOwner.lifecycle.addObserver(viewStateLifecycleObserver)
    }

    private fun saveState() {
        viewModel.saveStates(binding.rvMoviesList)
    }

    private fun setViewsTags() {
        ViewStateHelper.setViewTag(
            binding.rvMoviesList,
            Constants.ViewsTags.RECYCLER_VIEW_MOVIES
        )
    }

    private fun bindGetMoviesObserver() {
        observe(viewModel.moviesResponseSharedFlow) {
            when (it.statusCode) {
                StatusCode.SUCCESS -> {
                    onMoviesListSuccess(it.data)
                }
                else -> {
                    onMoviesListFailed(it.error)
                }
            }
        }

    }

    private fun onMoviesListFailed(error: String?) {
        binding.swipeRefreshMovies.isVisible = false
        binding.errorLayout.root.isVisible = true
        binding.errorLayout.txtError.text =
            error.alternate(getString(R.string.some_thing_went_wrong))
        binding.errorLayout.btnRetry.isVisible = true
    }

    private fun onMoviesListSuccess(data: ArrayList<Movie>?) {
        binding.swipeRefreshMovies.isVisible = true
        binding.errorLayout.root.isVisible = false
        if(!data.isNullOrEmpty()){
            viewModel.restoreViewState(this,binding.rvMoviesList)
            moviesAdapter.replaceItems(data)
        }else{
            binding.emptyLayout.root.isVisible = true
        }
    }


    private fun bindLoadingObserver() {
        observe(viewModel.loadingObservable) {
            onLoadingObserverRetrieved(it)
        }
    }


    private fun onLoadingObserverRetrieved(loadingModel: LoadingModel) {
        loadingModel.loadingProgressView = binding.viewProgress.loadingIndicator
        loadingModel.pullToRefreshProgressView = binding.swipeRefreshMovies
        loadingModel.loadingFullProgressView = binding.viewFullProgress.root
        loadingModel.pagingProgressView = binding.progressViewPaging.root
        showProgress(loadingModel)
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

    override fun onItemClick(item: Movie, position: Int) {
        navigateTo(MoviesListFragmentDirections.actionToMovieDetailsFragment(item.id ?: -1))
    }

    override fun onScroll() {
        viewModel.onScroll()
    }
}