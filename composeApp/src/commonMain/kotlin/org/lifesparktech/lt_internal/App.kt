package org.lifesparktech.lt_internal

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    val scope = rememberCoroutineScope()
    var payments by remember { mutableStateOf<List<Payment>>(emptyList()) }
    LaunchedEffect(true) {
        scope.launch {
            payments = Greeting().greeting()
        }
    }
    MaterialTheme {
        Column {
            LazyColumn {
                items(payments.size) { index ->
                    PaymentRow(payments[index], index)
                }
            }
        }
        //Index||Order ID||Status||Time

    }

}

@Composable
@Preview
fun PaymentRow(payment: Payment, index: Int) {

    Row(modifier = Modifier.padding(16.dp)) {
        Text(
            index.toString(),
            modifier = Modifier.weight(1f).padding(start = 4.dp).wrapContentWidth(Alignment.Start),
        )
        Divider(color = MaterialTheme.colorScheme.secondary)
        Text(
            payment.order_id.toString(),
            modifier = Modifier.weight(1f).padding(start = 4.dp).wrapContentWidth(Alignment.Start),
        )
        Text(
            payment.status,
            modifier = Modifier.weight(1f).padding(start = 4.dp).wrapContentWidth(Alignment.CenterHorizontally),
        )
        Text(
            payment.created_at.toString(),
            modifier = Modifier.weight(1f).padding(start = 4.dp).wrapContentWidth(Alignment.Start),
        )
    }
}