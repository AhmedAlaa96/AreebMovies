package com.ahmed.areebmovies.ui.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class BaseActivity<out VB : ViewBinding> : AppCompatActivity() {
    protected lateinit var mContext: Context


    protected abstract fun onActivityCreated(savedInstanceState: Bundle?)

    protected abstract fun initializeViews()

    protected abstract fun setListeners()

    private var mViewBinding: VB? = null

    protected abstract val viewBindingInflater: (LayoutInflater) -> VB

    @Suppress("UNCHECKED_CAST")
    protected val binding: VB
        get() = mViewBinding as VB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mViewBinding = viewBindingInflater.invoke(layoutInflater)
        setContentView(requireNotNull(mViewBinding).root)
        mContext = this
        initializeViews()
        onActivityCreated(savedInstanceState)
        setListeners()
    }

    // Replace/Add Fragment
    protected fun replaceFragment(
            containerId: Int, fragment: Fragment, tag: String,
            shouldAddToBackStack: Boolean = false, backStackTag: String? = null
    ) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        if (shouldAddToBackStack)
            fragmentTransaction.addToBackStack(backStackTag)
        fragmentTransaction.replace(containerId, fragment, tag)
                .commit()
    }

    protected fun addFragment(
            containerId: Int, fragment: Fragment, tag: String,
            shouldAddToBackStack: Boolean = false, backStackTag: String? = null
    ) {
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        if (shouldAddToBackStack)
            fragmentTransaction.addToBackStack(backStackTag)
        fragmentTransaction.add(containerId, fragment, tag)
                .commit()
    }

    override fun onDestroy() {
        super.onDestroy()
        mViewBinding = null
    }
}
