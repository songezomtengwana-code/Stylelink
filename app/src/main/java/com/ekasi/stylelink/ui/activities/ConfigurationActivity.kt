package com.ekasi.stylelink.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.ekasi.stylelink.data.viewModels.UserViewModel
import com.ekasi.stylelink.databinding.ActivityConfigurationBinding
import com.ekasi.stylelink.ui.components.CustomProgressDialog
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth

@Suppress("DEPRECATION")
class ConfigurationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityConfigurationBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var firebaseUser: FirebaseUser
    private lateinit var profileImage: ImageView
    private lateinit var username: TextView
    private lateinit var signOutButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConfigurationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        profileImage = binding.profileImage
        username =  binding.profileUsername


        auth = Firebase.auth
        val userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        signOutButton = binding.signOutButton

        signOutButton.setOnClickListener {
            signOut(userViewModel)
        }

        isUserActive(auth, userViewModel)

    }

    private fun isUserActive(auth: FirebaseAuth, userViewModel: UserViewModel) {
        val user = auth.currentUser
        if (user != null) {
            firebaseUser = user
            loadUserInfo(userViewModel)
        } else {
            val signInActivity = Intent(this, SignInActivity::class.java)
            startActivity(signInActivity)
        }
    }

    private fun loadUserInfo(userViewModel: UserViewModel) {
        val activeUser = userViewModel.getLoggedInUserData()
        val _user = userViewModel.loggedInUser.value
        Log.d("User Value Test", "${_user}")
        Log.d("loadUserInfo", "active user: ${activeUser?.username}")
        username.text = activeUser?.username
        Glide.with(baseContext).load(activeUser?.profileImageURL).into(profileImage)
    }
    private fun signOut(UserViewModel: UserViewModel) {
        val dialog = CustomProgressDialog(this)
        val signInActivity = Intent(this, SignInActivity::class.java)
        signInActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)

        dialog.show()

        Handler(Looper.getMainLooper()).postDelayed({
            dialog.dismiss()
        }, 10000)

        try {
            UserViewModel.clearLoggedInUser()
            auth.signOut()
        } catch (e: Exception) {
            Log.d("signOutUser", "Unable to get active user")
        } finally {
            Log.d("signOutProcess", "Yey!! We Signed Out Successfully")
            Handler().postDelayed({
                try {
                    startActivity(signInActivity)
                    dialog.dismiss()
                } catch (e: Exception) {
                    Log.d("signOutProcess", "For Some Reason We Is Unable To Log Out")
                }
            }, 2500)
        }
    }
}