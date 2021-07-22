package com.mx.starter_demo

import android.Manifest
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.mx.starter.MXPermission
import com.mx.starter.MXStarter

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.success).setOnClickListener {
            MXStarter.start(this, ResultActivity::class.java) { code, data ->
                Toast.makeText(
                    this@MainActivity,
                    data?.getStringExtra("ID") ?: "null",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
        findViewById<Button>(R.id.permission).setOnClickListener {
            MXPermission.requestPermission(
                this,
                arrayOf(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.CAMERA,
                    Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
            ) { allowed, p ->
                Toast.makeText(
                    this@MainActivity,
                    "获取权限：$allowed  ${p.joinToString(",")}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}