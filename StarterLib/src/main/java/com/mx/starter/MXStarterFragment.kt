package com.mx.starter

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import java.util.*

class MXStarterFragment : Fragment() {
    companion object {
        private val LISTENER_MAP: TreeMap<Int, ((resultCode: Int, data: Intent?) -> Unit)> =
            TreeMap<Int, ((resultCode: Int, data: Intent?) -> Unit)>()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    fun startActivity(intent: Intent?, listener: ((resultCode: Int, data: Intent?) -> Unit)) {
        val requestCode = getRequestCode()
        LISTENER_MAP[requestCode] = listener
        startActivityForResult(intent, requestCode)
    }

    private fun getRequestCode(): Int {
        return if (LISTENER_MAP.isEmpty()) {
            1
        } else {
            LISTENER_MAP.lastKey() + 1
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        LISTENER_MAP.remove(requestCode)?.invoke(resultCode, data)
    }
}