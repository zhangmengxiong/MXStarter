package com.mx.starter

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import java.util.*

class MXPrivateFragment : Fragment() {
    companion object {
        private const val FRAGMENT_TAG = "MXStarterFragment"

        /**
         * 启动器结果集
         */
        private val ACTIVITY_RESULT_MAP: TreeMap<Int, ((resultCode: Int, data: Intent?) -> Unit)> =
            TreeMap<Int, ((resultCode: Int, data: Intent?) -> Unit)>()

        /**
         * 权限结果集
         */
        private val PERMISSION_RESULT_MAP: TreeMap<Int, ((allowed: Boolean) -> Unit)> =
            TreeMap<Int, ((allowed: Boolean) -> Unit)>()


        fun getStarterFragment(fragmentManager: FragmentManager): MXPrivateFragment {
            var mxStarterFragment = findActResultFragment(fragmentManager)
            if (mxStarterFragment == null) {
                mxStarterFragment = MXPrivateFragment()
                fragmentManager
                    .beginTransaction()
                    .add(mxStarterFragment, FRAGMENT_TAG)
                    .commitNow()
            }
            return mxStarterFragment
        }

        private fun findActResultFragment(fragmentManager: FragmentManager): MXPrivateFragment? {
            return fragmentManager.findFragmentByTag(FRAGMENT_TAG) as MXPrivateFragment?
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    fun startActivity(intent: Intent?, listener: ((resultCode: Int, data: Intent?) -> Unit)) {
        val requestCode = getActivityRequestCode()
        ACTIVITY_RESULT_MAP[requestCode] = listener
        startActivityForResult(intent, requestCode)
    }

    private fun getActivityRequestCode(): Int {
        return if (ACTIVITY_RESULT_MAP.isEmpty()) {
            1
        } else {
            ACTIVITY_RESULT_MAP.lastKey() + 1
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        ACTIVITY_RESULT_MAP.remove(requestCode)?.invoke(resultCode, data)
    }

    fun requestPermission(permissions: Array<String>, result: ((allowed: Boolean) -> Unit)) {
        val requestCode = getPermissionRequestCode()
        PERMISSION_RESULT_MAP[requestCode] = result
        requestPermissions(permissions, requestCode)
    }

    private fun getPermissionRequestCode(): Int {
        return if (PERMISSION_RESULT_MAP.isEmpty()) {
            1
        } else {
            PERMISSION_RESULT_MAP.lastKey() + 1
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        val allowed = hasPermission(requireContext(), permissions.toList().toTypedArray())
        PERMISSION_RESULT_MAP.remove(requestCode)?.invoke(allowed)
    }

    private fun hasPermission(context: Context, array: Array<String>): Boolean {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true
        }
        val list = ArrayList<String>()
        array.forEach {
            if (ContextCompat.checkSelfPermission(
                    context,
                    it
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                list.add(it)
            }
        }
        return list.isEmpty()
    }
}