package com.ahmed.areebmovies.ui.base

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Nullable
import androidx.annotation.StyleRes
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.ahmed.areebmovies.data.models.LoadingModel
import com.ahmed.areebmovies.data.models.ProgressTypes
import com.ahmed.areebmovies.utils.Utils
import com.ahmed.areebmovies.utils.utilities.UIUtils
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.ahmed.sampleappmvvmrx.ui.base.IBaseFragment


abstract class BaseBottomSheetFragment<VB : ViewBinding> : BottomSheetDialogFragment(),
    IBaseFragment {

    override var fragment: Fragment
        get() = this
        set(_) {}
    protected lateinit var mContext: Context
    private var mBehavior: BottomSheetBehavior<View>? = null
    protected abstract val disableCollapsing: Boolean

    @get:StyleRes
    protected abstract val styleResId: Int?

    private var mViewBinding: ViewBinding? = null

    abstract val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> VB

    protected abstract fun onActivityReady(@Nullable savedInstanceState: Bundle?)

    protected abstract fun initViews()

    protected abstract fun setListeners()

    protected abstract fun initViewModels(arguments: Bundle?)

    protected abstract fun bindViewModels()

    protected abstract fun showError(shouldShow: Boolean)

    @Suppress("UNCHECKED_CAST")
    protected val binding: VB
        get() = mViewBinding as VB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        styleResId?.let {
            setStyle(STYLE_NORMAL, it)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mViewBinding = bindingInflater.invoke(inflater, container, false)
        return requireNotNull(mViewBinding).root
    }


    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        val view = bindingInflater.invoke(LayoutInflater.from(context), null, false).root
        dialog.setContentView(view)
        if (disableCollapsing) {
            mBehavior = BottomSheetBehavior.from(view.parent as View)
            mBehavior?.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
                override fun onStateChanged(bottomSheet: View, newState: Int) {
                    if (newState == BottomSheetBehavior.STATE_COLLAPSED) {
                        mBehavior?.state = BottomSheetBehavior.STATE_EXPANDED
                    }
                }

                override fun onSlide(bottomSheet: View, slideOffset: Float) {}
            })
            dialog.setOnShowListener {
                setupFullHeight()
            }
        }
        return dialog
    }

    override fun onStart() {
        super.onStart()
        if (disableCollapsing)
            mBehavior?.state = BottomSheetBehavior.STATE_EXPANDED
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (activity != null)
            mContext = this.activity as Context

        initViews()
        initViewModels(arguments)
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
            is String -> UIUtils.showToast(mContext, message)
            is Int -> UIUtils.showToast(mContext, getString(message))
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

    private fun setupFullHeight() {
        val layoutParams = binding.root.layoutParams
        val windowHeight = getWindowHeight()
        if (layoutParams != null) {
            layoutParams.height = windowHeight
        }
        binding.root.layoutParams = layoutParams
        mBehavior?.state = BottomSheetBehavior.STATE_EXPANDED
    }

    private fun getWindowHeight(): Int {
        val displayMetrics = DisplayMetrics()
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
            (mContext as Activity?)!!.display?.getRealMetrics(displayMetrics)
        } else {
            @Suppress("DEPRECATION")
            (mContext as Activity?)!!.windowManager.defaultDisplay.getMetrics(displayMetrics)
        }
        return displayMetrics.heightPixels
    }

    override fun onDestroyView() {
        clearViewBinding()
        super.onDestroyView()
    }
}
