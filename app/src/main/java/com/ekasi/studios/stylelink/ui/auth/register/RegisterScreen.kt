package com.ekasi.studios.stylelink.ui.auth.register

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.ekasi.studios.stylelink.base.components.LoadingDialog
import com.ekasi.studios.stylelink.navigation.Screen
import com.ekasi.studios.stylelink.ui.theme.StylelinkTheme
import com.ekasi.studios.stylelink.ui.theme.smallSize
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

@Composable
fun RegisterScreen(viewModel: RegisterViewModel) {
    val snackbarHostState = remember { SnackbarHostState() }

    var fullname: String by rememberSaveable { mutableStateOf("") }
    var state: String by rememberSaveable { mutableStateOf("") }
    var address: String by rememberSaveable { mutableStateOf("") }
    var city: String by rememberSaveable { mutableStateOf("") }
    var phone: String by rememberSaveable { mutableStateOf("") }
    val password: String by rememberSaveable { mutableStateOf("password") }
    var avatar: String by rememberSaveable {
        mutableStateOf("")
    }
    var passwordHidden by rememberSaveable { mutableStateOf(true) }
    var checkBox by rememberSaveable { mutableStateOf(false) }

    // View
    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
    ) { contentPadding ->
        Surface(
            modifier = Modifier
                .padding(contentPadding)
                .verticalScroll(rememberScrollState())
        ) {
            Column(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.background)
                    .padding(16.dp)
            ) {
                Text(
                    "Okay, lets set up your profile",
                    style = MaterialTheme.typography.displayLarge
                )
                Text(
                    "Lets properly set up your profile to be the best."
                )
                Spacer(modifier = Modifier.height(12.dp))
//                Row(
//                    horizontalArrangement = Arrangement.Start,
//                    verticalAlignment = Alignment.CenterVertically
//                ) {
//                    OutlinedCard(
//                        modifier = Modifier
//                            .height(76.dp)
//                            .width(76.dp)
//                            .verticalScroll(rememberScrollState())
//                            .clickable { Log.d("uploadProfileImage", "initiating") },
//                    ) {
//                        if (avatar.isEmpty()) {
//                            Icon(
//                                imageVector = Icons.Rounded.Person,
//                                contentDescription = "profile_placeholder",
//                                modifier = Modifier.size(76.dp)
//                            )
//                        } else {
//                            AsyncImage(model = avatar, contentDescription = "user_profile_image")
//                        }
//                    }
//                    Spacer(modifier = Modifier.width(12.dp))
//                    Text(
//                        text = "Click to upload image",
//                        style = MaterialTheme.typography.labelLarge
//                    )
//                }
                Spacer(modifier = Modifier.height(12.dp))
                OutlinedTextField(
                    value = fullname,
                    onValueChange = { fullname = it },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth(),
                    label = { Text("Full Name") },
                )
                Spacer(modifier = Modifier.height(12.dp))
                OutlinedTextField(
                    value = phone,
                    onValueChange = { phone = it },
                    singleLine = true,
                    prefix = { Text("+27", fontWeight = FontWeight.Bold) },
                    modifier = Modifier
                        .fillMaxWidth(),
                    label = { Text("Phone Number") },
                )

                Spacer(modifier = Modifier.height(12.dp))
                OutlinedTextField(
                    value = address,
                    onValueChange = { address = it },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth(),
                    label = { Text("Address (Optional)") },
                )
                Spacer(modifier = Modifier.height(12.dp))
                OutlinedTextField(
                    value = city,
                    onValueChange = { city = it },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth(),
                    label = { Text("City (Optional)") },
                )
                Spacer(modifier = Modifier.height(12.dp))
                OutlinedTextField(
                    value = state,
                    onValueChange = { state = it },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth(),
                    label = { Text("Province / State (Optional)") },
                )
                Spacer(modifier = Modifier.height(12.dp))

                Spacer(modifier = Modifier.height(12.dp))
                Button(
                    onClick = {
                        viewModel.registerUserAccount(
                            fullname,
                            viewModel.firebaseUser?.email.toString(),
                            password,
                            phone,
                            address,
                            city, state
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(smallSize),
                    shape = MaterialTheme.shapes.small
                ) {
                    Text("Complete", fontWeight = FontWeight.Bold)
                }
                Spacer(modifier = Modifier.height(12.dp))
                OutlinedButton(
                    enabled = false,
                    onClick = { viewModel.navigateTo(Screen.Home.route) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(smallSize),
                    shape = MaterialTheme.shapes.small
                ) {
                    Text("Not yet ? Skip", fontWeight = FontWeight.Bold)
                }
                Spacer(modifier = Modifier.height(10.dp))
                if (viewModel.firebaseUser !== null) Text(text = viewModel.firebaseUser!!.email!!)
                if (viewModel.isLoading) {
                    LoadingDialog()
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RegisterScreenPreview() {
    StylelinkTheme {
    }
}
