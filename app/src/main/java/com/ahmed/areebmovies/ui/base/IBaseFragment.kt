package com.ahmed.sampleappmvvmrx.ui.base

import android.net.Uri
import androidx.annotation.IdRes
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import androidx.navigation.fragment.findNavController

interface IBaseFragment {


    var fragment: Fragment

    fun navigateTo(
            navDirections: NavDirections, navigatorExtras: Navigator.Extras? = null,
            navOptions: NavOptions? = null
    ) {
        try {
            val navController = fragment.findNavController()
            when {
                navigatorExtras == null -> {
                    navController
                            .navigate(navDirections, navOptions)
                }
                navOptions == null -> navController
                        .navigate(navDirections, navigatorExtras)
                else -> navController.navigate(navDirections, navOptions)
            }
        } catch (exc: Exception) {
            exc.printStackTrace()
        }
    }

    fun navigateTo(
            actionId: Int,
            navOptions: NavOptions? = null,
            navigatorExtras: Navigator.Extras? = null
    ) {
        val navController = fragment.findNavController()
        if (navigatorExtras == null) {
            navController
                    .navigate(actionId, null, navOptions)
        } else
            navController.navigate(actionId, null, navOptions, navigatorExtras)
    }

    fun navigateTo(deepLink: Uri) {
        fragment.findNavController().navigate(deepLink)
    }

    fun navigateTo(
            deepLink: Uri, navigatorExtras: Navigator.Extras? = null,
            navOptions: NavOptions? = null
    ) {
        fragment.findNavController().navigate(deepLink, navOptions, navigatorExtras)
    }


    fun navigateToAndChangeStartDestination(startDestination: Int, actionId: Int,
                                            navOptions: NavOptions? = null,
                                            navigatorExtras: Navigator.Extras? = null) {
        fragment.findNavController().graph.setStartDestination(startDestination)
        navigateTo(actionId, navOptions, navigatorExtras)
    }

    fun navigateToAndChangeStartDestination(startDestination: Int, navDirections: NavDirections,
                                            navOptions: NavOptions? = null,
                                            navigatorExtras: Navigator.Extras? = null) {
        fragment.findNavController().graph.setStartDestination(startDestination)
        navigateTo(navDirections, navigatorExtras, navOptions)
    }


    fun navigateUp() {
        fragment.findNavController().navigateUp()
    }

    fun popBackStack(@IdRes destinationId: Int? = null, inclusive: Boolean = false) {
        val navController = fragment.findNavController()
        if (destinationId != null) {
            navController.popBackStack(destinationId, inclusive)
        } else {
            if (!navController.popBackStack()) {
                fragment.requireActivity().finish()
            }
        }
    }


    fun showDialogFragment(fragment: DialogFragment, fragmentManager: FragmentManager, tag: String) {
        fragment.show(fragmentManager, tag)
    }


}