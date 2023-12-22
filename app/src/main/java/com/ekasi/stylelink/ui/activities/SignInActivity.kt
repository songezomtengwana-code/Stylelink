@file:Suppress("DEPRECATION")

package com.ekasi.stylelink.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.ekasi.stylelink.databinding.ActivitySignInBinding
import com.ekasi.stylelink.ui.components.CustomProgressDialog
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class SignInActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignInBinding
    private lateinit var email: TextInputEditText
    private lateinit var password: TextInputEditText
    private lateinit var signInButton: Button
    private lateinit var resetPasswordConfigurator: Button
    private lateinit var contextView: View
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        email = binding.signInEmail
        password = binding.signInPassword
        signInButton =binding.signInButton
        contextView = binding.signInContext
        resetPasswordConfigurator = binding.resetPasswordConfigurator

        firebaseAuth = Firebase.auth
        val homeIntent = Intent(this, HomeActivity::class.java)
        signInButton.setOnClickListener {
            val email = email.text.toString()
            val password = password.text.toString()
            authenticateUser(email, password, homeIntent)
        }

        val redirectButton: Button = binding.redirectButton
        redirectButton.setOnClickListener {
            val createAccountIntent = Intent(this, SignUpActivity::class.java)

            startActivity(createAccountIntent)
        }

        resetPasswordConfigurator.setOnClickListener {
            val resetPasswordIntent = Intent(this, PasswordResetActivity::class.java)
            startActivity(resetPasswordIntent)
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    private fun authenticateUser(email:String, password: String, homeIntent: Intent) {
        if (email.isEmpty()  || password.isEmpty()) {
            snackbarPrompt("please fill both fields")
        } else if (email.isEmpty()) {
            snackbarPrompt("please enter a valid email")
        } else if (password.isEmpty()) {
            snackbarPrompt("password cant be less than 1 - 4 character")
        } else {
            val dialog = CustomProgressDialog(this)
            dialog.show()

            Handler(Looper.getMainLooper()).postDelayed({
                dialog.dismiss()
            }, 10000)
            firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this) {task ->
                if (task.isSuccessful) {
                    val user = firebaseAuth.currentUser
                    Log.d("sign_in_1", "User authenticated welcome back ${user?.displayName}")

                    startActivity(homeIntent)

                } else {
                    Log.d("sign_in_1", "authentication failed")
                    snackbarPrompt("cant login user", "Try Again")
                    dialog.dismiss()
                }
            }
        }
    }

    private fun snackbarPrompt(message: String, option: String? = "") {
        if (option!!.isEmpty()) {
            Snackbar.make(contextView, message, Snackbar.LENGTH_LONG).show()
        } else  {
            Snackbar.make(contextView, message, Toast.LENGTH_LONG).setAction(option) {
            }.show()
        }
    }

}