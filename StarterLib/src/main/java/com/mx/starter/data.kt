package com.mx.starter

import android.content.Intent

data class ActivityResultData(
    val fragmentKey: Long,
    val resultInvoke: ((resultCode: Int, data: Intent?) -> Unit)
)

data class PermissionResultData(
    val fragmentKey: Long,
    val resultInvoke: ((allowed: Boolean, un_permissions: Array<String>) -> Unit)
)