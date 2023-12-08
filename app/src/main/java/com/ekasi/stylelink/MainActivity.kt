package com.ekasi.stylelink

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import com.ekasi.stylelink.databinding.ActivityMainBinding
import com.ekasi.stylelink.ui.activities.HomeActivity
import com.ekasi.stylelink.ui.activities.SignInActivity
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val splashTimeOut: Long = 5000 // note -> milliseconds

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)

        Handler().postDelayed({
            val user = Firebase.auth.currentUser
            if (user != null) {
                val homeActivity = Intent(this, HomeActivity::class.java)
                startActivity(homeActivity)
            } else {
                val intent = Intent(this, SignInActivity::class.java)
                startActivity(intent)
                Log.d("Splash Screen", "User has to authenticate")
            }
            finish()
        }, splashTimeOut)


    }
}