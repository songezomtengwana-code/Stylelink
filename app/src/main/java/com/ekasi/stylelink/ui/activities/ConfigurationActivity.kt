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
import com.ekasi.stylelink.data.models.UserModel
import com.ekasi.stylelink.data.viewModels.UserViewModel
import com.ekasi.stylelink.databinding.ActivityConfigurationBinding
import com.ekasi.stylelink.ui.components.CustomProgressDialog
import com.ekasi.stylelink.util.network.NetworkClient
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Suppress("DEPRECATION")
class ConfigurationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityConfigurationBinding
    private lateinit var currentActiveUser: UserModel
    private lateinit var auth: FirebaseAuth
    private lateinit var firebaseUser: FirebaseUser
    private lateinit var profileImage: ImageView
    private lateinit var username: TextView
    private lateinit var signOutButton: Button
    private lateinit var userViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConfigurationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        profileImage = binding.profileImage
        username =  binding.profileUsername

        auth = Firebase.auth
        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        signOutButton = binding.signOutButton

        signOutButton.setOnClickListener {
            signOut(userViewModel)
        }

        isUserActive(auth)

    }

    private fun isUserActive(auth: FirebaseAuth) {
        val user = auth.currentUser
        if (user != null) {
            firebaseUser = user
            try {
                getActiveUser(user.email.toString())
            } catch (error: Exception) {
                Log.d("isUserActive", "${error.message.toString()}")
            } finally {
                loadUserInfo()
            }
        } else {
            val signInActivity = Intent(this, SignInActivity::class.java)
            startActivity(signInActivity)
        }
    }

    private fun getActiveUser(email: String) {
        NetworkClient.NetworkClient.userService.getUserSingleAccount(email).enqueue(object :
            Callback<UserModel> {
            override fun onFailure(call: Call<UserModel>, t: Throwable) {
                Log.d("getActiveUser", "No user was seen")
            }

            override fun onResponse(call: Call<UserModel>, response: Response<UserModel>) {
                if (response.isSuccessful) {
                    var activeUser = response.body()
                    userViewModel.saveLoggedInUser(activeUser)
                    currentActiveUser = userViewModel.getLoggedInUserData()!!
                    Log.d("loadUserInfo", "active user: ${currentActiveUser?.username}")
                    username.text = currentActiveUser?.username
                    Glide.with(baseContext).load(currentActiveUser?.profileImageURL).into(profileImage)
                } else {
                    // handle error
                    Log.d("getActiveUser", "User Not Available")
                }
            }
        })
    }

    private fun loadUserInfo() {

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