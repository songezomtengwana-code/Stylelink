package com.ekasi.stylelink.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.AutoCompleteTextView
import com.ekasi.stylelink.R
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.auth

class ConfigurationActivity : AppCompatActivity() {
    private lateinit var stateOptions: AutoCompleteTextView

    private lateinit var auth: FirebaseAuth
    private lateinit var firebaseUser: FirebaseUser
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_configuration)

        auth = Firebase.auth
        isUserActive(auth)

        val items = arrayOf("Item 1", "Item 2", "Item 3", "Item 4")
        stateOptions= findViewById(R.id.stateOptions)
        (stateOptions.editableText as? MaterialAutoCompleteTextView)?.setSimpleItems(items)

    }

    private fun isUserActive(auth: FirebaseAuth) {
        val user = auth.currentUser
        if (user != null) {
            firebaseUser = user
        } else {
            val signUpIntent = Intent(this, SignUpActivity::class.java)
            startActivity(signUpIntent)
        }
    }
}