package com.mx.starter

import android.app.Activity
import android.content.Intent
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

object MXStarter {
    fun start(
        fragment: Fragment,
        clazz: Class<out Activity>,
        result: ((resultCode: Int, data: Intent?) -> Unit)? = null
    ) {
        val intent = Intent(fragment.requireActivity(), clazz)
        start(fragment.requireActivity(), intent, result)
    }

    fun start(
        fragment: Fragment,
        intent: Intent,
        result: ((resultCode: Int, data: Intent?) -> Unit)? = null
    ) {
        start(fragment.requireActivity(), intent, result)
    }

    fun start(
        activity: FragmentActivity,
        clazz: Class<out Activity>,
        result: ((resultCode: Int, data: Intent?) -> Unit)? = null
    ) {
        val intent = Intent(activity, clazz)
        start(activity, intent, result)
    }

    fun start(
        activity: FragmentActivity,
        intent: Intent,
        result: ((resultCode: Int, data: Intent?) -> Unit)? = null
    ) {
        if (result == null) {
            activity.startActivity(intent)
            return
        }
        val starterFragment = MXPrivateFragment.getStarterFragment(activity.supportFragmentManager)
        starterFragment.startActivity(intent, result)
    }
}