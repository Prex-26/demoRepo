package org.lifesparktech.lt_internal

import androidx.compose.foundation.interaction.HoverInteraction
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.Checkbox
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.*
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

import org.jetbrains.compose.ui.tooling.preview.Preview
import kotlin.collections.mutableListOf

@Composable
@Preview
fun App() {
    val scope = rememberCoroutineScope()
    var payments by remember { mutableStateOf<List<Payment>>(emptyList()) }
    var checkBoxStatus by remember { mutableStateOf(mutableStateListOf(false)) }
    LaunchedEffect(Unit) {
        scope.launch {
            payments = Greeting().greeting()
//            checkBoxStatus = MutableList(payments.size) { false } as SnapshotStateList<Boolean>

        }
    }
    MaterialTheme {
        Column {
            LazyColumn {
                items(payments.size) { index ->

                    PaymentRow(payments[index], index)
                    Checkbox(
                        checked = checkBoxStatus[index],
                        onCheckedChange = { checkBoxStatus[index] = it },
                        modifier = Modifier.weight(1f).padding(start = 4.dp)
                            .wrapContentWidth(Alignment.CenterHorizontally),
                    )
                }
            }
        }
        //Index||Order ID||Status||Time

    }

}

@Composable
@Preview
fun PaymentRow(payment: Payment, index: Int) {
    Row() {
        Text(
            index.toString(),
            modifier = Modifier.weight(0.5f).padding(start = 4.dp).wrapContentWidth(Alignment.Start),
        )
        VerticalDivider(
            modifier = Modifier
                .height(20.dp)
                .width(1.dp)
        )
        Text(
            payment.order_id.toString(),
            modifier = Modifier.weight(1f).padding(start = 4.dp).wrapContentWidth(Alignment.CenterHorizontally),
        )
        VerticalDivider(
            modifier = Modifier
                .height(20.dp)
                .width(1.dp)
        )
        Text(
            payment.status,
            modifier = Modifier.weight(1f).padding(start = 4.dp).wrapContentWidth(Alignment.CenterHorizontally),
        )
        VerticalDivider(
            modifier = Modifier
                .height(20.dp)
                .width(1.dp)
        )
        Text(
            Instant.fromEpochSeconds(payment.created_at).toLocalDateTime(TimeZone.currentSystemDefault()).toString(),
            modifier = Modifier.weight(1f).padding(start = 4.dp).wrapContentWidth(Alignment.CenterHorizontally),
        )
        VerticalDivider(
            modifier = Modifier
                .height(20.dp)
                .width(1.dp)
        )
        Text(
            payment.amount.toString(),
            modifier = Modifier.weight(0.5f).padding(start = 4.dp).wrapContentWidth(Alignment.CenterHorizontally),
        )
    }

}