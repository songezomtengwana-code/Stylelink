package com.ekasi.studios.stylelink.ui.main

import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.ekasi.studios.stylelink.base.common.composables.white100
import com.ekasi.studios.stylelink.base.components.ActionButton
import com.ekasi.studios.stylelink.base.components.SolidNavbar

@Composable
fun MainScreen(viewModel: MainViewModel) {

    val backDispatcher = LocalOnBackPressedDispatcherOwner.current

    LaunchedEffect(Unit) {

    }

    Scaffold(
        modifier = Modifier
            .background(white100),
        topBar = { SolidNavbar() }
    ) { paddingValues ->
        Surface(
            modifier = Modifier
                .padding(paddingValues)
                .background(Color.White)
                .fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .background(white100)
            ) {
                ActionButton(onClick = { viewModel.signOut() }, title = "Log In")
                Text("MainScreen", style = MaterialTheme.typography.displayLarge)
                Text("MainScreen", style = MaterialTheme.typography.displayLarge)
                Text("MainScreen", style = MaterialTheme.typography.displayLarge)
                Text("MainScreen", style = MaterialTheme.typography.displayLarge)
                Text("MainScreen", style = MaterialTheme.typography.displayLarge)
                Text("MainScreen", style = MaterialTheme.typography.displayLarge)
                Text("MainScreen", style = MaterialTheme.typography.displayLarge)
                Text("MainScreen", style = MaterialTheme.typography.displayLarge)
                Text("MainScreen", style = MaterialTheme.typography.displayLarge)
                Text("MainScreen", style = MaterialTheme.typography.displayLarge)
            }
        }


    }
}