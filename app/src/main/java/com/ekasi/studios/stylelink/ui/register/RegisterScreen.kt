package com.ekasi.studios.stylelink.ui.register

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ekasi.studios.stylelink.R
import com.ekasi.studios.stylelink.base.components.LoadingDialog
import com.ekasi.studios.stylelink.ui.theme.StylelinkTheme
import com.ekasi.studios.stylelink.ui.theme.smallSize
import com.ekasi.studios.stylelink.ui.theme.tinySize

@Composable
fun RegisterScreen(viewModel: RegisterViewModel) {
    // Snackbar State
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    // Global State
    var fullname: String by rememberSaveable { mutableStateOf("Songezo Mtengwana") }
    var email: String by rememberSaveable { mutableStateOf("songezomtengwana@gmail.com") }
    var phone: String by rememberSaveable { mutableStateOf("0793534874") }
    var password: String by rememberSaveable { mutableStateOf("S34ND0NLIF3") }
    var passwordHidden by rememberSaveable { mutableStateOf(false) }
    var checkBox by rememberSaveable { mutableStateOf(true) }

    // View
    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
//        floatingActionButton = {
//            ExtendedFloatingActionButton(
//                text = { Text("Show snackbar") },
//                icon = { Icon(Icons.Filled.ArrowForward, contentDescription = "") },
//                onClick = {
//                    scope.launch {
//                        snackbarHostState.showSnackbar("Snackbar")
//                    }
//                }
//            )
//        }
    ) { contentPadding ->
//        if (viewModel.isLoading) {

//        }
        Surface(
            modifier = Modifier
                .padding(contentPadding)
        ) {
            LoadingDialog()
            Column(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.background)
                    .padding(16.dp)
            ) {
                Text("Create Your New Account", style = MaterialTheme.typography.displayLarge)
                Text(
                    "Keeping up with beauty trends ? Create an account and lets help you get started."
                )
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
                    value = email,
                    onValueChange = { email = it },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth(),
                    label = { Text("Email Address") },
                )
                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = phone,
                    onValueChange = { phone = it },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth(),
                    label = { Text("Phone Number") },
                )
                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth(),
                    label = { Text("password") },
                    visualTransformation =
                    if (passwordHidden) PasswordVisualTransformation() else VisualTransformation.None,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    trailingIcon = {
                        IconButton(onClick = { passwordHidden = !passwordHidden }) {
                            val visibilityIcon =
                                if (passwordHidden) R.drawable.eye else R.drawable.eye_closed
                            // Please provide localized description for accessibility services
                            val description =
                                if (passwordHidden) "Show password" else "Hide password"
                            Icon(
                                painterResource(id = visibilityIcon),
                                contentDescription = description,
                                modifier = Modifier
                                    .height(
                                        tinySize
                                    )
                                    .width(tinySize)
                            )
                        }
                    }
                )
                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    Checkbox(checked = checkBox, onCheckedChange = { checkBox = !checkBox })
                    Text(
                        text = "I have fully read and understand to Stylelink Inc. privacy policy and terms of service.",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))

                Button(
                    enabled = checkBox,
                    onClick = {
                        viewModel.registerUserAccount(
                            fullname,
                            email,
                            password,
                            phone,
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(smallSize),
                    shape = MaterialTheme.shapes.small
                ) {
                    Text("Create Account", fontWeight = FontWeight.Bold)
                }
                Spacer(modifier = Modifier.height(10.dp))
                if (viewModel.isLoading) {
                    LinearProgressIndicator(
                        modifier = Modifier.fillMaxWidth(),
                        color = MaterialTheme.colorScheme.secondary,
                        trackColor = MaterialTheme.colorScheme.primary,
                    )
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
