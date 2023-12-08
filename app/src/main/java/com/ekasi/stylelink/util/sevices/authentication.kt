package com.ekasi.stylelink.util.sevices

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import com.ekasi.stylelink.api.NetworkClient
import com.ekasi.stylelink.data.models.NewUserModel
import com.ekasi.stylelink.data.models.UserModel
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.auth.userProfileChangeRequest
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class authentication {

}
//
//private fun  auth(email: String, password: String, username: String, context: Context) {
//    val firebaseAuth = Firebase.auth
//
//    if (email.isNotEmpty()|| password.isNotEmpty()) {
//        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener() {
//                task ->
//            if (task.isSuccessful)  {
//                Log.d("Create Account", "Success, Account Create On Firebase")
//                val user = firebaseAuth.currentUser
//
//                val profileUpdates = userProfileChangeRequest {
//                    displayName = username
//                }
//
//                user!!.updateProfile(profileUpdates)
//                    .addOnCompleteListener { task ->
//                        if (task.isSuccessful) {
//                            Log.d("Profile Update", "User profile updated.")
//                        }
//                    }
//
//            } else {
//                Log.w("Create Account", "signInWithCustomToken:failure", task.exception)
//                Toast.makeText(context, "Authentication failed.",
//                    Toast.LENGTH_SHORT).show()
//            }
//        }
//    } else {
//        Log.d("Create Account", "No values passed")
//    }
//}
//
//private fun createUserAccount(data: NewUserModel, intent: Intent, context: Context) {
//    NetworkClient.NetworkClient.apiService.createUserAccount(data)
//        .enqueue(object : Callback<ResponseBody> {
//            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
//                if (response.isSuccessful) {
//                    val responseBody = response.body()
//                    Log.d("Create Account Response", responseBody.toString())
//                    auth(data.email, data.password, data.username, context)
////                    startActivity(intent)
//                } else {
//                    Log.d("Create Error Response", "Error Creating A New User")
//                    Toast.makeText(context, "Error creating account, please try again", Toast.LENGTH_LONG).show()
////                    createAccountIndicator.visibility = View.GONE
//                }
//            }
//
//            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
//                Log.d("Create Error Response", "shit MF did not even start")
//                Toast.makeText(context, "Error creating account, please try again", Toast.LENGTH_LONG).show()
////                Snackbar.make(context, "Error create account, please try again", Snackbar.LENGTH_LONG).show()
////                createAccountIndicator.visibility = View.GONE
//            }
//        })
//}