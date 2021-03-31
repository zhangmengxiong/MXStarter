package com.mx.starter

import android.content.Intent
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

object MXStarter {
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
        val starterFragment = MXPrivateFragment.getStarterFragment(activity.supportFragmentManager)
        starterFragment.startActivity(intent, result)
    }
}