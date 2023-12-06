package com.ekasi.stylelink.ui.activities

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import com.ekasi.stylelink.R
import com.ekasi.stylelink.data.models.UserModel
import com.ekasi.stylelink.databinding.ActivitySignUpBinding
import com.google.android.material.progressindicator.LinearProgressIndicator
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import java.util.Date
import java.util.UUID

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private lateinit var username: TextInputEditText
    private lateinit var email: TextInputEditText
    private lateinit var mobile: TextInputEditText
    private lateinit var password: TextInputEditText
    private lateinit var createAccountButton: Button
    private lateinit var logInButton: Button
    private lateinit var createAccountIndicator: LinearProgressIndicator
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        binding = ActivitySignUpBinding.inflate(layoutInflater)

        username = findViewById(R.id.username)
        email = findViewById(R.id.email)
        mobile = findViewById(R.id.phone_number)
        password = findViewById(R.id.password)
        createAccountButton = findViewById(R.id.createAccountButton)
        logInButton = findViewById(R.id.logInButton)
        createAccountIndicator = findViewById(R.id.createAccountIndicator)
        createAccountIndicator.visibility = View.GONE

        createAccountButton.setOnClickListener{
            val userId = UUID.randomUUID().toString()
            val email = email.text.toString()
            val username = username.text.toString()
            val mobile = mobile.text.toString()
            val password = password.text.toString()
            val registrationDate = Date()
            val termsCheckBox = findViewById<CheckBox>(R.id.termsCheckBox)
            val context = findViewById<View>(R.id.signUpContext)
            val invalidSnackbar = Snackbar.make(context, R.string.invalid_input_values, Snackbar.LENGTH_LONG).setAction(R.string.invalid_input_values_action) {}

            if (email.isNotEmpty() || username.isNotEmpty() || password.isNotEmpty() || mobile.isNotEmpty()) {
                if (termsCheckBox.isChecked)  {
                    createAccountIndicator.visibility = View.VISIBLE;
                    val newUserAccount = UserModel(
                        userId,
                        username,
                        email,
                        password,
                        mobile,
                        registrationDate,
                    )
                    println(newUserAccount)
                } else {
                    Snackbar.make(context, "Please make sure you've check the box below", Snackbar.LENGTH_LONG).show()
                }
            } else {
                invalidSnackbar.setBackgroundTint(Color.RED)
                invalidSnackbar.setActionTextColor(Color.WHITE)
                invalidSnackbar.setTextColor(Color.WHITE)
                invalidSnackbar.show()
            }

        }

        logInButton.setOnClickListener {
            val loginIntent = Intent(this, SignInActivity::class.java)
            startSignIn(loginIntent)
        }
    }

    private fun startSignIn(i: Intent) {
        startActivity(i)
    }

    private fun validateInput (email: String, username: String,mobile: String, password: String): Boolean {

        // currently broken
        return email.isNotEmpty() || username.isNotEmpty() || password.isNotEmpty() || mobile.isNotEmpty() || mobile.length > 8
    }


}