package com.ekasi.studios.stylelink.ui.register

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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
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
import androidx.navigation.NavController
import com.ekasi.studios.stylelink.R
import com.ekasi.studios.stylelink.base.common.composables.StylelinkTheme
import com.ekasi.studios.stylelink.ui.theme.smallSize
import com.ekasi.studios.stylelink.ui.theme.tinySize

@Composable
fun RegisterScreen(viewModel: RegisterViewModel, navController: NavController) {

    // State
    var fullname: String by rememberSaveable { mutableStateOf("") }
    var email: String by rememberSaveable { mutableStateOf("") }
    var phone: String by rememberSaveable { mutableStateOf("") }
    var password: String by rememberSaveable { mutableStateOf("") }
    var passwordHidden by rememberSaveable { mutableStateOf(true) }
    var checkBox by rememberSaveable { mutableStateOf(false) }


    // View
    Surface(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.background)
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
                        val description = if (passwordHidden) "Show password" else "Hide password"
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
                    text = "I have fully read and understand to Stylelink Inc. terms of service.",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            Spacer(modifier = Modifier.height(12.dp))

            Button(
                enabled = checkBox,
                onClick = { viewModel.registerUserAccount(fullname, email, password, phone, navController) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(smallSize),
                shape = MaterialTheme.shapes.small
            ) {
                Text("Create Account", fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RegisterScreenPreview() {
    StylelinkTheme {
//        RegisterScreen(viewModel = RegisterViewModel)
    }
}
