package com.ainrom.cocktail.utils

import android.app.Activity
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class PermissionsDelegate internal constructor(private val activity: Activity) {

    private val permissions = arrayOf(
        android.Manifest.permission.ACCESS_NETWORK_STATE,
        android.Manifest.permission.ACCESS_WIFI_STATE,
        android.Manifest.permission.INTERNET
    )

    fun hasPermissions(): Boolean {
        var shouldAsk = false

        for (permission in permissions) {
            if (ContextCompat.checkSelfPermission(
                    activity,
                    permission
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                shouldAsk = true
                break
            }
        }
        return !shouldAsk
    }

    fun requestPermissions() {
        ActivityCompat.requestPermissions(
            activity, permissions, REQUEST_CODE
        )
    }

    fun resultGranted(
        requestCode: Int, grantResults: IntArray
    ): Boolean {
        return requestCode == REQUEST_CODE && grantResults.isNotEmpty()
    }

    companion object {
        private const val REQUEST_CODE = 1983
    }
}
