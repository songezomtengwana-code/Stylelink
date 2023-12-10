package com.ekasi.stylelink.ui.activities

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.ekasi.stylelink.R
import com.ekasi.stylelink.data.models.UserModel
import com.ekasi.stylelink.data.viewModels.UserViewModel
import com.ekasi.stylelink.databinding.ActivityHomeBinding
import com.ekasi.stylelink.util.network.NetworkClient
import com.google.android.material.progressindicator.LinearProgressIndicator
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Calendar

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var userViewModel: UserViewModel
    private lateinit var activeUser: UserModel
    private lateinit var homeUsername: TextView
    private lateinit var homeGreeting:TextView
    private lateinit var homeLoader: LinearLayout
    private lateinit var homeContent: ScrollView
    private lateinit var homeProfileAvatar: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        homeUsername = findViewById(R.id.homeUsername)
        homeGreeting = binding.homeGreeting
        homeLoader = binding.homeLoader
        homeContent = binding.homeContent
        homeContent.visibility= View.GONE
        homeProfileAvatar = binding.homeProfileAvatar
        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        main()
    }

    private fun main() {
        val firebaseUser = Firebase.auth.currentUser

        if (firebaseUser != null) {
            getActiveUser(firebaseUser.email.toString())
            greetingConfiguration()
        } else {
            val signUpIntent = Intent(this, SignInActivity::class.java)
            startActivity(signUpIntent)
            Toast.makeText(baseContext, "Session Expired Please Log In Again", Toast.LENGTH_LONG).show()
        }
    }

    private fun getActiveUser(email: String) {
        NetworkClient.NetworkClient.userService.getUserSingleAccount(email).enqueue(object : Callback<UserModel> {
            override fun onFailure(call: Call<UserModel>, t: Throwable) {
                Log.d("getActiveUser", "MF did not even look !")
                val homeLoadingTextView = binding.homeLoaderText
                homeLoadingTextView.text = "Error"
                try {
                    Snackbar.make(binding.homeViewContainer, "Sorry, we came across an error", Snackbar.LENGTH_LONG)
                        .setTextColor(Color.WHITE)
                        .setBackgroundTint(Color.RED)
                        .setActionTextColor(Color.WHITE)
                        .setAction("Reload") {
                            homeLoadingTextView.text = "Loading"
                            try {
                                main()
                            } catch (err: Exception) {
                                Log.d("Home Reload", err.message!!)
                            }
                        }
                } catch (err: Exception) {
                    Log.d("onFailure", err.message!!)
                }
            }

            override fun onResponse(call: Call<UserModel>, response: Response<UserModel>) {
                if (response.isSuccessful) {
                    val retrievedUser = response.body()

                    // save user in ViewModel
                    userViewModel.saveLoggedInUser(retrievedUser)
                    if (retrievedUser?.profileImageURL?.length!! < 0) {
                        Glide.with(baseContext).load("https://stylelinkapi.onrender.com/api/cloud/d164a270-cd95-4aed-b63d-58435cc073f0").into(homeProfileAvatar)
                    } else {
                        Glide.with(baseContext).load(retrievedUser.profileImageURL).into(homeProfileAvatar)
                    }
                    activeUser = userViewModel.getLoggedInUserData()!!
                    homeUsername.text = activeUser.username
                    homeContent.visibility= View.VISIBLE
                    homeLoader.visibility = View.GONE
                    Log.d("getActiveUser", "Logged User: ${activeUser.username}")
                } else {
                    // handle error
                    Log.d("getActiveUser", "User Not Available")
                }
            }
        })
    }

    private fun greetingConfiguration() {
        val calender = Calendar.getInstance()
        val hour = calender.get(Calendar.HOUR_OF_DAY)

        val morningGreeting = "Good morning"
        val afternoonGreeting = "Good afternoon"
        val eveningGreeting = "Good evening"

        if (hour < 12) {
            homeGreeting.text = morningGreeting
        } else if (hour < 18) {
            homeGreeting.text = afternoonGreeting
        } else {
            homeGreeting.text = eveningGreeting
        }
    }

    private fun profileRedirect() {

    }

}