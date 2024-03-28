package com.mx.starter

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

object MXPermission {
    /**
     * @param fragment
     * @param permissions 权限列表
     * @param result
     *      allowed：是否通过全部权限申请
     *      un_permissions：未通过的权限列表
     */
    fun requestPermission(
        fragment: Fragment,
        permissions: Array<String>,
        result: ((allowed: Boolean, un_permissions: Array<String>) -> Unit)? = null
    ) {
        requestPermission(fragment.requireActivity(), permissions, result)
    }

    /**
     * @param activity
     * @param permissions 权限列表
     * @param result
     *      allowed：是否通过全部权限申请
     *      un_permissions：未通过的权限列表
     */
    fun requestPermission(
        activity: FragmentActivity,
        permissions: Array<String>,
        result: ((allowed: Boolean, un_permissions: Array<String>) -> Unit)? = null
    ) {
        val list = getNoPermissionList(activity, permissions)
        if (list.isEmpty()) {
            result?.invoke(true, emptyArray())
            return
        }
        if (result == null) {
            ActivityCompat.requestPermissions(activity, list.toTypedArray(), 0x11)
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
                    context, it
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                list.add(it)
            }
        }
        return list
    }
}