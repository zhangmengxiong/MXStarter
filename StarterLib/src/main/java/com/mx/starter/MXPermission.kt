package com.mx.starter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

object MXPermission {

    fun requestPermission(
        activity: FragmentActivity,
        permissions: Array<String>,
        result: ((allowed: Boolean) -> Unit)
    ) {
        val starterFragment = MXPrivateFragment.getStarterFragment(activity.supportFragmentManager)
        starterFragment.requestPermission(permissions, result)
    }

    fun requestPermission(
        fragment: Fragment,
        permissions: Array<String>,
        result: ((allowed: Boolean) -> Unit)
    ) {
        requestPermission(fragment.requireActivity(), permissions, result)
    }
}