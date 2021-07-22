package com.mx.starter_demo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import kotlin.random.Random

class ResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        findViewById<Button>(R.id.success).setOnClickListener {
            setResult(RESULT_OK, Intent().apply {
                putExtra("ID", "asdasd " + Random.nextInt(1000))
            })
            finish()
        }
        findViewById<Button>(R.id.fail).setOnClickListener {
            setResult(RESULT_CANCELED)
            finish()
        }
    }
}