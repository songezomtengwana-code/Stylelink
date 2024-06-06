@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

package com.ekasi.studios.stylelink.base.components.servicemodal

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.ekasi.studios.stylelink.R
import com.ekasi.studios.stylelink.base.composable.CheckBoxWithText
import com.ekasi.studios.stylelink.data.model.Service
import com.ekasi.studios.stylelink.ui.theme.tinySize

@Composable
fun ServiceModal(
    showBottomSheetState: Boolean,
    service: Service,
    onStartBookingClick: () -> Unit = {},
    onDismissRequest: () -> Unit = {},
) {
    var policyAgreement = false
    var showBottomSheet by remember { mutableStateOf(showBottomSheetState) }

    LaunchedEffect(key1 = showBottomSheet) {
        showBottomSheet = showBottomSheetState
    }

    val sheetState = rememberModalBottomSheetState()
    ModalBottomSheet(
        sheetState = sheetState,
        onDismissRequest = {
            showBottomSheet = false
        },
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(tinySize)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(tinySize)
        ) {
            Text(
                text = service.name,
                style = MaterialTheme.typography.displayMedium,
                color = MaterialTheme.colorScheme.primary
            )
            Text(
                text = "Service Description",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = service.description,
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                text = "Service Details",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.cash_rounded),
                    contentDescription = "icon",
                    modifier = Modifier
                        .height(20.dp)
                        .width(tinySize)
                )
                Text(
                    text = "R${service.price}",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.time_rounded),
                    contentDescription = "icon",
                    modifier = Modifier
                        .height(tinySize)
                        .width(tinySize)
                )
                Text(
                    text = "${service.duration / 100000} Minutes",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                )
            }
            Text(
                text = "Cancellation Policy",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = service.description,
                color = MaterialTheme.colorScheme.onBackground
            )
            CheckBoxWithText(
                onCheckedChange = { policyAgreement = !policyAgreement },
                checkBox = policyAgreement,
                text = "I have fully read and agree with the cancellation policy."
            )
            Column(
                modifier = Modifier
            ) {

                Row {
                    OutlinedButton(
                        onClick = onDismissRequest,
                        modifier = Modifier
                            .padding(0.dp, 8.dp),
                    ) {
                        Text(
                            text = "Cancel",
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(
                        onClick = onStartBookingClick,
                        modifier = Modifier
                            .padding(0.dp, 8.dp)
                            .weight(1f)
                    ) {
                        Text(
                            text = "Start Booking",
                            style = MaterialTheme.typography.labelMedium,
                        )
                    }
                }
            }
        }
    }
}