package com.mx.starter

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import java.util.*
import java.util.concurrent.atomic.AtomicLong

class MXPrivateFragment : Fragment(), LifecycleObserver {
    companion object {
        private const val PRIVATE_TAG = "MXPrivateFragment"
        private val FRAGMENT_INCREASE = AtomicLong(1)
        private val SYNC_LOCK = Object()

        /**
         * 启动器结果集
         */
        private val ACTIVITY_RESULT_MAP = TreeMap<Int, ActivityResultData>()

        /**
         * 权限结果集
         */
        private val PERMISSION_RESULT_MAP = TreeMap<Int, PermissionResultData>()


        fun getStarterFragment(fragmentManager: FragmentManager): MXPrivateFragment {
            var mxStarterFragment =
                (fragmentManager.findFragmentByTag(PRIVATE_TAG) as MXPrivateFragment?)
            if (mxStarterFragment == null) {
                mxStarterFragment = MXPrivateFragment()
                fragmentManager
                    .beginTransaction()
                    .add(mxStarterFragment, PRIVATE_TAG)
                    .commitNow()
            }
            return mxStarterFragment
        }
    }

    private val fragmentKey = FRAGMENT_INCREASE.incrementAndGet()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
        activity?.lifecycle?.addObserver(this)
    }

    fun startActivity(intent: Intent?, listener: ((resultCode: Int, data: Intent?) -> Unit)) {
        synchronized(SYNC_LOCK) {
            val requestCode = if (ACTIVITY_RESULT_MAP.isEmpty()) {
                10
            } else {
                ACTIVITY_RESULT_MAP.lastKey() + 1
            }
            ACTIVITY_RESULT_MAP[requestCode] = ActivityResultData(fragmentKey, listener)
            startActivityForResult(intent, requestCode)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        synchronized(SYNC_LOCK) {
            ACTIVITY_RESULT_MAP.remove(requestCode)?.resultInvoke?.invoke(
                resultCode,
                data
            )
        }
    }

    fun requestPermission(
        permissions: Array<String>,
        result: ((allowed: Boolean, un_permissions: Array<String>) -> Unit)
    ) {
        synchronized(SYNC_LOCK) {
            val requestCode = if (PERMISSION_RESULT_MAP.isEmpty()) {
                10
            } else {
                PERMISSION_RESULT_MAP.lastKey() + 1
            }
            PERMISSION_RESULT_MAP[requestCode] = PermissionResultData(fragmentKey, result)
            requestPermissions(permissions, requestCode)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        val un_permissions = MXPermission.getNoPermissionList(
            requireContext(),
            permissions.toList().toTypedArray()
        )
        synchronized(SYNC_LOCK) {
            PERMISSION_RESULT_MAP.remove(requestCode)?.resultInvoke
                ?.invoke(un_permissions.isEmpty(), un_permissions.toTypedArray())
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun cleanResultMao() {
        // Activity退出后，释放请求回调
        synchronized(SYNC_LOCK) {
            ACTIVITY_RESULT_MAP.entries.filter {
                it.value.fragmentKey == fragmentKey
            }.forEach {
                ACTIVITY_RESULT_MAP.remove(it.key)
            }

            PERMISSION_RESULT_MAP.entries.filter {
                it.value.fragmentKey == fragmentKey
            }.forEach {
                PERMISSION_RESULT_MAP.remove(it.key)
            }
        }
    }
}