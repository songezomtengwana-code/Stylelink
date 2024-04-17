package com.ekasi.studios.stylelink.ui.main

import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.ekasi.studios.stylelink.base.common.composables.white100
import com.ekasi.studios.stylelink.base.components.ActionButton
import com.ekasi.studios.stylelink.base.components.SolidNavbar
import com.ekasi.studios.stylelink.data.model.ServerUserModel

@Composable
fun MainScreen(viewModel: MainViewModel) {

    val backDispatcher = LocalOnBackPressedDispatcherOwner.current
    var userid by rememberSaveable {
        mutableStateOf("")
    }

    LaunchedEffect(Unit) {
        viewModel.configuration()
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
                    .padding(16.dp)
            ) {
//                TopHeader(items = viewModel.user.value)
                ActionButton(onClick = { viewModel.signOut() }, title = "Log Out")
                Spacer(modifier = Modifier.height(10.dp))
                Text(text = viewModel.protoUserDetailsUserId, style = MaterialTheme.typography.bodyMedium)
                Spacer(modifier = Modifier.height(10.dp))
                ActionButton(onClick = { viewModel.getUserDetails()}, title = "Get User Id")
            }
        }
    }
}

//@Composable
//fun TopHeader(items: List<ServerUserModel>?) {
//    if (items!!.isNotEmpty()) {
//        items.forEach { user: ServerUserModel ->
//            Text(text = "Hi, ${user.fullname} 👋")
//        }
//    } else {
//        Text(text = "FailSafe")
//    }
//}