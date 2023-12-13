package com.ekasi.stylelink.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.ekasi.stylelink.R
import com.ekasi.stylelink.data.models.UserModel
import com.ekasi.stylelink.data.viewModels.UserViewModel
import com.ekasi.stylelink.databinding.ActivityConfigurationBinding
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth

class ConfigurationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityConfigurationBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var firebaseUser: FirebaseUser
    private lateinit var backButtonImageButton: ImageButton
    private lateinit var profileImage: ImageView
    private lateinit var username: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityConfigurationBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        backButtonImageButton = binding.backButton
        profileImage = binding.profileImage
        username =  binding.profileUsername
        auth = Firebase.auth
        isUserActive(auth)
        backButtonImageButton.setOnClickListener {
            finish()
        }

    }

    override fun onStart() {
        super.onStart()

        val userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        loadUserInfo(userViewModel)
    }

    private fun isUserActive(auth: FirebaseAuth) {
        val user = auth.currentUser
        if (user != null) {
            firebaseUser = user
        } else {
            val signInActivity = Intent(this, SignInActivity::class.java)
            startActivity(signInActivity)
        }
    }

    private fun loadUserInfo(userViewModel: UserViewModel) {
        val activeUser = userViewModel.getLoggedInUserData()
        try {
            if (activeUser?.profileImageURL?.length!! < 0) {
                Glide.with(baseContext).load("https://stylelinkapi.onrender.com/api/cloud/d164a270-cd95-4aed-b63d-58435cc073f0").into(profileImage)
            } else {
                Glide.with(baseContext).load(activeUser.profileImageURL).into(profileImage)
                username.text = activeUser.username
            }
        } catch (error: Exception) {
            Log.d("loadUserInfo", "Unable go get user data")
        } finally {
            Log.d("loadUserInfo", "----- completed tasks -----")
        }
    }
}