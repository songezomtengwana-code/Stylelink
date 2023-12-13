package com.ekasi.stylelink.ui.activities

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.ekasi.stylelink.data.models.UserModel
import com.ekasi.stylelink.data.viewModels.UserViewModel
import com.ekasi.stylelink.databinding.ActivityHomeBinding
import com.ekasi.stylelink.util.network.NetworkClient
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var userViewModel: UserViewModel
    private lateinit var activeUser: UserModel
    private lateinit var homeLoader: LinearLayout
    private lateinit var homeContent: ScrollView
    private lateinit var homeProfileAvatar: ImageView
    private lateinit var greetingTextView: TextView
    private lateinit var reloadImageView: ImageView
    private lateinit var homeProfileAvatarContainerCardView: CardView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // var declarations
        homeLoader = binding.homeLoader
        homeContent = binding.homeContent
        reloadImageView = binding.reloadImageView
        homeProfileAvatarContainerCardView = binding.homeProfileAvatarContainer

        // toggle visibility state
        homeContent.visibility= View.GONE
        reloadImageView.visibility= View.GONE
        homeProfileAvatar = binding.homeProfileAvatar
        userViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        // reload active screen
        reloadImageView.setOnClickListener {
            try {
                main()
            } catch (error: Exception) {
                Log.d("reloadImageView", error.message!!)
            }
        }

        // reroute to profile configuration screen
        homeProfileAvatarContainerCardView.setOnClickListener {
            val targetIntent = Intent(this, ConfigurationActivity::class.java)

            if (activeUser != null) {
                startActivity(targetIntent)
            } else {
                Snackbar.make(binding.homeViewContainer, "Please refresh page", Snackbar.LENGTH_LONG).setAction("Refresh") {
                    main()
                }
            }
        }

        // main thread functions
        main()
    }

    private fun main() {
        val firebaseUser = Firebase.auth.currentUser

        if (firebaseUser != null) {
            getActiveUser(firebaseUser.email.toString())
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
                val homeLoader = binding.progressIndicator
                homeLoader.visibility = View.GONE
                reloadImageView.visibility = View.VISIBLE
                homeLoadingTextView.text = "Unable to connect to servers"
                try {
                    val snack = Snackbar.make(binding.homeViewContainer, "Unable to connect to servers", 12000)
                        snack.setTextColor(Color.WHITE)
                        snack.setBackgroundTint(Color.BLACK)
                        snack.setActionTextColor(Color.WHITE)
                        snack.setAction("Try Again") {
                            homeLoadingTextView.text = "Loading..."
                            homeLoader.visibility = View.VISIBLE
                            reloadImageView.visibility = View.GONE
                            try {
                                main()
                            } catch (err: Exception) {
                                Log.d("Home Reload", err.message!!)
                            }
                        }
                        snack.show()
                } catch (err: Exception) {
                    Log.d("onFailure", err.message!!)
                }
            }

            override fun onResponse(call: Call<UserModel>, response: Response<UserModel>) {
                if (response.isSuccessful) {
                    val retrievedUser = response.body()
                    greetingTextView = binding.greeting

                    // save user in ViewModel
                    userViewModel.saveLoggedInUser(retrievedUser)
                    if (retrievedUser?.profileImageURL?.length!! < 0) {
                        Glide.with(baseContext).load("https://stylelinkapi.onrender.com/api/cloud/d164a270-cd95-4aed-b63d-58435cc073f0").into(homeProfileAvatar)
                    } else {
                        Glide.with(baseContext).load(retrievedUser.profileImageURL).into(homeProfileAvatar)
                        greetingTextView.text = "Hi, ${retrievedUser.username} 👋"
                    }
                    activeUser = userViewModel.getLoggedInUserData()!!
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

}