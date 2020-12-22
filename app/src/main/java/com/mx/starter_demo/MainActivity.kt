package com.mx.starter_demo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
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
    }
}