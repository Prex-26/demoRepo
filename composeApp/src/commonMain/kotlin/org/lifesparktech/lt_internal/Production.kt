package org.lifesparktech.lt_internal

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.Button
import androidx.compose.material.Checkbox
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.compose.ui.tooling.preview.Preview


@Composable
@Preview
@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
fun OrdersView(navController: NavHostController) {
    val scope = rememberCoroutineScope()
    var payments by remember { mutableStateOf<List<Payment>>(emptyList()) }
    var checkBoxStatus by remember { mutableStateOf(SnapshotStateList<Boolean>()) }
    var isButtonEnabled by remember { mutableStateOf(false) }
    val maxSelections = 3

    LaunchedEffect(Unit) {
        scope.launch {
            payments = ServerCommunication().getOrders()
            checkBoxStatus = SnapshotStateList<Boolean>().apply {
                addAll(List(payments.size) { false })
            }
        }
    }

    Column {
        Row(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
            Text("Index", modifier = Modifier.weight(0.5f).padding(start = 4.dp))
            VerticalDivider(modifier = Modifier.height(20.dp).width(1.dp))
            Text("Order ID", modifier = Modifier.weight(1f).padding(start = 4.dp))
            VerticalDivider(modifier = Modifier.height(20.dp).width(1.dp))
            Text("Status", modifier = Modifier.weight(1f).padding(start = 4.dp))
            VerticalDivider(modifier = Modifier.height(20.dp).width(1.dp))
            Text("Created At", modifier = Modifier.weight(1f).padding(start = 4.dp))
            VerticalDivider(modifier = Modifier.height(20.dp).width(1.dp))
            Text("Amount", modifier = Modifier.weight(1f).padding(start = 4.dp))
            VerticalDivider(modifier = Modifier.height(20.dp).width(1.dp))
            Text("Status", modifier = Modifier.weight(1f).padding(start = 4.dp))
            VerticalDivider(modifier = Modifier.height(20.dp).width(1.dp))
            Text("Select", modifier = Modifier.weight(1f).padding(start = 4.dp))
        }

        LazyColumn {
            items(payments.size) { index ->
                Row {
                    var payment = payments[index]
                    var expanded by remember { mutableStateOf(false) }
                    var selectedStatus by remember { mutableStateOf("Received") }
                    val statusOptions = listOf("Received", "In process", "Done", "Dispatched")

                    Row(modifier = Modifier.fillMaxWidth(0.8f)) {
                        Text(index.toString(), modifier = Modifier.weight(0.5f).padding(start = 4.dp))
                        VerticalDivider(modifier = Modifier.height(20.dp).width(1.dp))
                        Text(payment.order_id.toString(), modifier = Modifier.weight(1f).padding(start = 4.dp))
                        VerticalDivider(modifier = Modifier.height(20.dp).width(1.dp))
                        Text(payment.status, modifier = Modifier.weight(1f).padding(start = 4.dp))
                        VerticalDivider(modifier = Modifier.height(20.dp).width(1.dp))
                        Text(Instant.fromEpochSeconds(payment.created_at).toLocalDateTime(TimeZone.currentSystemDefault()).toString(), modifier = Modifier.weight(1f).padding(start = 4.dp))
                        VerticalDivider(modifier = Modifier.height(20.dp).width(1.dp))
                        Text(payment.amount.toString(), modifier = Modifier.weight(0.5f).padding(start = 4.dp))
                        VerticalDivider(modifier = Modifier.height(20.dp).width(1.dp))
                        ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = { expanded = !expanded }) {
                            OutlinedTextField(
                                value = selectedStatus,
                                onValueChange = { },
                                readOnly = true,
                                label = { Text("Select Status") },
                                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                                colors = TextFieldDefaults.outlinedTextFieldColors(),
                                modifier = Modifier.weight(1f).clickable { expanded = !expanded }
                            )
                            DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                                statusOptions.forEach { status ->
                                    DropdownMenuItem(onClick = {
                                        selectedStatus = status
                                        expanded = false
                                    }) {
                                        Text(text = status)
                                    }
                                }
                            }
                        }
                    }
                    Checkbox(
                        checked = checkBoxStatus[index],
                        onCheckedChange = { isChecked ->
                            val selectedCount = checkBoxStatus.count { it }
                            if (isChecked && selectedCount >= maxSelections) return@Checkbox
                            checkBoxStatus[index] = isChecked
                            isButtonEnabled = checkBoxStatus.any { it }
                        },
                        modifier = Modifier.weight(1f).padding(start = 4.dp)
                    )
                }
            }
        }
        Button(
            onClick = {
                val selectedOrderIds = payments.filterIndexed { index, _ -> checkBoxStatus[index] }
                    .map { it.order_id }
                scope.launch {
                    navController.navigate("FlashingScreen/${selectedOrderIds.joinToString(",")}")
                }
            },
            enabled = isButtonEnabled,
            modifier = Modifier.padding(16.dp)
        ) {
            Text("Submit")
        }
    }
}

@Composable
fun FlashingScreen(navController: NavHostController, selectedOrderIds: String) {
    val orderIds = selectedOrderIds.split(",").map { it }
    Column {
        Text("Selected Order IDs: $orderIds")
        Button(onClick = { navController.popBackStack() }, modifier = Modifier.padding(16.dp)) {
            Text("Back")
        }
    }
}