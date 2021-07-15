package com.mx.starter

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

object MXPermission {
    fun requestPermission(
        fragment: Fragment,
        permissions: Array<String>,
        result: ((allowed: Boolean) -> Unit)
    ) {
        requestPermission(fragment.requireActivity(), permissions, result)
    }

    fun requestPermission(
        activity: FragmentActivity,
        permissions: Array<String>,
        result: ((allowed: Boolean) -> Unit)
    ) {
        val list = getNoPermissionList(activity, permissions)
        if (list.isEmpty()) {
            result.invoke(true)
            return
        }
        val starterFragment = MXPrivateFragment.getStarterFragment(activity.supportFragmentManager)
        starterFragment.requestPermission(list.toTypedArray(), result)
    }

    /**
     * 获取没有给的权限
     */
    fun getNoPermissionList(context: Context, permissions: Array<String>): List<String> {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return emptyList()
        }
        val list = ArrayList<String>()
        permissions.forEach {
            if (ContextCompat.checkSelfPermission(
                    context,
                    it
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                list.add(it)
            }
        }
        return list
    }
}