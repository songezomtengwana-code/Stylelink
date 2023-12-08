@file:Suppress("DEPRECATION")

package com.ekasi.stylelink.ui.activities

import android.app.ProgressDialog
import android.app.ProgressDialog.show
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.ekasi.stylelink.R
import com.ekasi.stylelink.databinding.ActivitySignInBinding
import com.google.android.material.progressindicator.LinearProgressIndicator
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth

class SignInActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignInBinding
    private lateinit var email: TextInputEditText
    private lateinit var password: TextInputEditText
    private lateinit var signInButton: Button
    private lateinit var contextView: View
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        binding = ActivitySignInBinding.inflate(layoutInflater)

        email = findViewById(R.id.signInEmail)
        password = findViewById(R.id.signInPassword)
        signInButton = findViewById(R.id.signInButton)
        contextView = findViewById(R.id.signInContext)

        val signInIndicator = findViewById<LinearProgressIndicator>(R.id.signInIndicator)

        signInIndicator.visibility = View.GONE

        firebaseAuth = Firebase.auth
        val homeIntent = Intent(this, HomeActivity::class.java)

        signInButton.setOnClickListener {
            show(this, "", "")

            val email = email.text.toString()
            val password = password.text.toString()
            authenticateUser(email, password, homeIntent)
            ProgressDialog.BUTTON_NEGATIVE
        }

        val redirectButton = findViewById<Button>(R.id.redirectButton)

        redirectButton.setOnClickListener {
            val createAccountIntent = Intent(this, SignUpActivity::class.java)

            startActivity(createAccountIntent)
        }

    }

    private fun authenticateUser(email:String, password: String, homeIntent: Intent) {
        if (email.isEmpty()  || password.isEmpty()) {
            snackbarPrompt("please fill both fields")
        } else if (email.isEmpty()) {
            snackbarPrompt("please enter a valid email")

        } else if (password.isEmpty()) {
            snackbarPrompt("password cant be less than 1 - 4 character")
        } else {
            firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this) {task ->
                if (task.isSuccessful) {
                    val user = firebaseAuth.currentUser
                    Log.d("sign_in_1", "User authenticated welcome back ${user?.displayName}")

                    startActivity(homeIntent)

                } else {
                    Log.d("sign_in_1", "authentication failed")
                    snackbarPrompt("Authentication Failed", "Try Again")
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