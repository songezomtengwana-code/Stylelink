package com.ekasi.stylelink

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.ekasi.stylelink.databinding.ActivityMainBinding
import com.ekasi.stylelink.ui.activities.SignUpActivity

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val splashTimeOut: Long = 2500 // note -> milliseconds

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)

        Handler().postDelayed({
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
            finish()
        }, splashTimeOut)
    }
}