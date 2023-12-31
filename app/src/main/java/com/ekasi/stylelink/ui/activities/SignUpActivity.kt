package com.ekasi.stylelink.ui.activities

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.Toast
import com.ekasi.stylelink.R
import com.ekasi.stylelink.util.network.NetworkClient.NetworkClient.apiService
import com.ekasi.stylelink.data.models.NewUserModel
import com.ekasi.stylelink.databinding.ActivitySignUpBinding
import com.google.android.material.progressindicator.LinearProgressIndicator
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.auth.userProfileChangeRequest
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Suppress("NAME_SHADOWING")
class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    private lateinit var username: TextInputEditText
    private lateinit var email: TextInputEditText
    private lateinit var mobile: TextInputEditText
    private lateinit var password: TextInputEditText
    private lateinit var createAccountButton: Button
    private lateinit var logInButton: Button
    private lateinit var createAccountIndicator: LinearProgressIndicator
    private lateinit var firebaseAuth: FirebaseAuth

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

        firebaseAuth = Firebase.auth

        createAccountButton.setOnClickListener{
            val email = email.text.toString()
            val username = username.text.toString()
            val phoneNumber = mobile.text.toString()
            val password = password.text.toString()
            val termsCheckBox = findViewById<CheckBox>(R.id.termsCheckBox)
            val context = findViewById<View>(R.id.signUpContext)
            val invalidSnackbar = Snackbar.make(context, R.string.invalid_input_values, Snackbar.LENGTH_LONG).setAction(R.string.invalid_input_values_action) {}


            if (email.isNotEmpty() || username.isNotEmpty() || password.isNotEmpty() || phoneNumber.isNotEmpty()) {
                if (termsCheckBox.isChecked)  {
                    createAccountIndicator.visibility = View.VISIBLE
                    val newUserAccount = NewUserModel(
                        username,
                        email,
                        password,
                        phoneNumber,
                    )

                    val homeActivity = Intent(this, HomeActivity::class.java)
                    createUserAccount(newUserAccount, homeActivity, context)
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

    private fun  auth(email: String, password: String, username: String) {
        if (email.isNotEmpty()|| password.isNotEmpty()) {
            firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this) {
                task ->
                if (task.isSuccessful)  {
                    Log.d("Create Account", "Success, Account Create On Firebase")
                    val user = firebaseAuth.currentUser

                    val profileUpdates = userProfileChangeRequest {
                        displayName = username
                    }

                    user!!.updateProfile(profileUpdates)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Log.d("Profile Update", "User profile updated.")
                            }
                        }

                    user.sendEmailVerification()
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                Log.d("Email Verification", "Email sent.")
                            }
                        }


                } else {
                    Log.w("Create Account", "signInWithCustomToken:failure", task.exception)
                    Toast.makeText(baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT).show()
                }
            }
        } else {
            Log.d("Create Account", "No values passed")
        }
    }

    private fun createUserAccount(data: NewUserModel, intent: Intent, context: View) {
        apiService.createUserAccount(data)
            .enqueue(object : Callback<ResponseBody> {
                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        Log.d("Create Account Response", responseBody.toString())
                        auth(data.email, data.password, data.username)

                        startActivity(intent)
                    } else {
                        Log.d("Create Error Response", "Error Creating A New User")
                        Snackbar.make(context, "Error create account, please try again", Snackbar.LENGTH_LONG).show()
                        createAccountIndicator.visibility = View.GONE
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Log.d("Create Error Response", "shit MF did not even start")
                    Snackbar.make(context, "Error create account, please try again", Snackbar.LENGTH_LONG).show()
                    createAccountIndicator.visibility = View.GONE
                }
            })
    }
}