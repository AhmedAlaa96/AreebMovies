package com.ahmed.areebmovies.ui.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.ahmed.areebmovies.data.models.LoadingModel
import com.ahmed.areebmovies.data.models.ProgressTypes
import com.ahmed.areebmovies.utils.Utils
import com.ahmed.areebmovies.utils.utilities.UIUtils.showToast
import com.ahmed.sampleappmvvmrx.ui.base.IBaseFragment


abstract class BaseFragment<VB : ViewBinding> : Fragment(), IBaseFragment {

    override var fragment: Fragment
        get() = this
        set(_) {}
    protected lateinit var mContext: Context

    private var mViewBinding: ViewBinding? = null


    abstract val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> VB

    protected abstract fun onActivityReady(@Nullable savedInstanceState: Bundle?)

    protected abstract fun initViews()

    protected abstract fun setListeners()

    protected abstract fun bindViewModels()

    protected abstract fun showError(shouldShow: Boolean)

    @Suppress("UNCHECKED_CAST")
    protected val binding: VB
        get() = mViewBinding as VB

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mViewBinding = bindingInflater.invoke(inflater, container, false)
        return requireNotNull(mViewBinding).root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null)
            mContext = this.activity as Context

        initViews()
        bindViewModels()
        onActivityReady(savedInstanceState)
        setListeners()
    }

    protected fun onError(exception: Throwable) {
        Utils.printStackTrace(exception)
    }

    protected open fun showProgress(loadingModel: LoadingModel) {
        when (loadingModel.progressType) {
            ProgressTypes.MAIN_PROGRESS ->
                loadingModel.loadingProgressView?.visibility =
                     if (loadingModel.shouldShow) View.VISIBLE else View.GONE
            ProgressTypes.PAGING_PROGRESS ->
                loadingModel.pagingProgressView?.visibility =
                    if (loadingModel.shouldShow) View.VISIBLE else View.GONE
            ProgressTypes.PULL_TO_REFRESH_PROGRESS -> {
                loadingModel.pullToRefreshProgressView?.isRefreshing = loadingModel.shouldShow
            }
            ProgressTypes.FULL_PROGRESS ->   loadingModel.loadingFullProgressView?.visibility =
                if (loadingModel.shouldShow) View.VISIBLE else View.GONE
        }
    }

    protected fun showMessage(message: Any) {
        when (message) {
            is String -> showToast(mContext, message)
            is Int -> showToast(mContext, getString(message))
        }
    }

    protected fun onUnAuthorized() {}

    // Replace/Add Fragment
    protected fun replaceFragmentFromHistory(
        containerId: Int, tag: String,
        shouldAddToBackStack: Boolean = false, backStackTag: String? = null
    ): Boolean {
        val fragmentByTag: Fragment = childFragmentManager.findFragmentByTag(tag) ?: return false
        val fragmentTransaction = childFragmentManager.beginTransaction()
        if (shouldAddToBackStack)
            fragmentTransaction.addToBackStack(backStackTag)
        fragmentTransaction.replace(containerId, fragmentByTag, tag)
            .commitNow()
        return true
    }

    protected fun replaceFragment(
        containerId: Int, fragment: Fragment, tag: String,
        shouldAddToBackStack: Boolean = false, backStackTag: String? = null
    ) {
        val fragmentTransaction = childFragmentManager.beginTransaction()
        if (shouldAddToBackStack)
            fragmentTransaction.addToBackStack(backStackTag)
        fragmentTransaction.replace(containerId, fragment, tag)
            .commit()
    }

    protected fun addFragment(
        containerId: Int, fragment: Fragment, tag: String,
        shouldAddToBackStack: Boolean = false, backStackTag: String? = null
    ) {
        val fragmentTransaction = childFragmentManager.beginTransaction()
        if (shouldAddToBackStack)
            fragmentTransaction.addToBackStack(backStackTag)
        fragmentTransaction.add(containerId, fragment, tag)
            .commit()
    }

    protected fun removeFragmentByTag(fragmentTag: String) {
        val fragmentTransaction = childFragmentManager.beginTransaction()
        val fragment = childFragmentManager.findFragmentByTag(fragmentTag)
        if (fragment != null)
            fragmentTransaction.remove(fragment)
                .commit()
    }

    private fun clearViewBinding() {
        mViewBinding = null
    }


    override fun onDestroyView() {
        clearViewBinding()
        super.onDestroyView()
    }
}
