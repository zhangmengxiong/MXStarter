package com.mx.starter

import android.content.Intent
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager

object MXStarter {
    private val TAG = MXStarter::class.java.name
    private fun getStarterFragment(fragmentManager: FragmentManager): MXStarterFragment {
        var mxStarterFragment = findActResultFragment(fragmentManager)
        if (mxStarterFragment == null) {
            mxStarterFragment = MXStarterFragment()
            fragmentManager
                .beginTransaction()
                .add(mxStarterFragment, TAG)
                .commitNow()
        }
        return mxStarterFragment
    }

    private fun findActResultFragment(fragmentManager: FragmentManager): MXStarterFragment? {
        return fragmentManager.findFragmentByTag(TAG) as MXStarterFragment?
    }

    fun start(
        fragment: Fragment,
        intent: Intent,
        result: ((resultCode: Int, data: Intent?) -> Unit)
    ) {
        start(fragment.requireActivity(), intent, result)
    }

    fun start(
        fragment: Fragment,
        clazz: Class<*>,
        result: ((resultCode: Int, data: Intent?) -> Unit)
    ) {
        val intent = Intent(fragment.requireActivity(), clazz)
        start(fragment.requireActivity(), intent, result)
    }

    fun start(
        activity: FragmentActivity,
        clazz: Class<*>,
        result: ((resultCode: Int, data: Intent?) -> Unit)
    ) {
        val intent = Intent(activity, clazz)
        start(activity, intent, result)
    }

    fun start(
        activity: FragmentActivity,
        intent: Intent,
        result: ((resultCode: Int, data: Intent?) -> Unit)
    ) {
        val starterFragment = getStarterFragment(activity.supportFragmentManager)
        starterFragment.startActivity(intent, result)
    }


}