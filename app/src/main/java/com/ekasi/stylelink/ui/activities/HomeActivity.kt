package com.ekasi.stylelink.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.ekasi.stylelink.data.models.UserModel
import com.ekasi.stylelink.data.viewModels.UserViewModel
import com.ekasi.stylelink.databinding.ActivityHomeBinding
import com.ekasi.stylelink.util.network.NetworkClient
import com.google.android.material.progressindicator.LinearProgressIndicator
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
    private lateinit var homeLoader: LinearProgressIndicator
    private lateinit var homeContent: ScrollView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        homeUsername = binding.homeUsername
        homeGreeting = binding.homeGreeting
        homeLoader = binding.homeLoader
        homeContent = binding.homeContent
        homeContent.visibility= View.GONE

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
            }

            override fun onResponse(call: Call<UserModel>, response: Response<UserModel>) {
                if (response.isSuccessful) {
                    val retrievedUser = response.body()
                    homeLoader.visibility = View.GONE
                    homeContent.visibility= View.VISIBLE
                    Log.d("getActiveUser", "User Retrieved Successfully")

                    // save user in ViewModel
                    userViewModel.saveLoggedInUser(retrievedUser)
                    activeUser = userViewModel.getLoggedInUserData()!!
                    homeUsername.text = activeUser.username
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

}