package com.ekasi.stylelink.ui.activities

import android.content.ActivityNotFoundException
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Button
import android.widget.Toast
import com.ekasi.stylelink.R
import com.ekasi.stylelink.databinding.ActivityPasswordResetBinding
import com.ekasi.stylelink.ui.components.CustomProgressDialog
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class PasswordResetActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPasswordResetBinding
    private lateinit var email: TextInputEditText
    private lateinit var submit: Button
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPasswordResetBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth
        email = binding.signInEmail
        submit = binding.submit

        submit.setOnClickListener {
            resetPassword()
        }
    }

    private fun resetPassword() {
        val signInIntent = Intent(this, SignInActivity::class.java)
        val emailConverter = email.text.toString()
        val dialog = CustomProgressDialog(this)
        dialog.show()

            auth.sendPasswordResetEmail(emailConverter).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(baseContext, "Email Sent Successfully", Toast.LENGTH_LONG).show()
                    Log.d("resetPassword", "Success")
                    dialog.dismiss()
//                    startActivity(signInIntent)

                    try {
                        val intent = Intent(Intent.ACTION_MAIN)
                        intent.addCategory(Intent.CATEGORY_APP_EMAIL)
                        startActivity(intent)
                    } catch (e: ActivityNotFoundException) {
                        // Handle the exception if no email app is found
                        Toast.makeText(baseContext, "No email app found", Toast.LENGTH_SHORT).show()
                    }

                } else {
                    Log.d("resetPassword", "##### Error #####")
                    dialog.dismiss()
                }
            }
    }
}