package org.lifesparktech.lt_internal

import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.benasher44.uuid.uuidFrom
import com.juul.kable.Filter
import com.juul.kable.Scanner
import com.juul.kable.peripheral
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {

    MaterialTheme {
        var navController= rememberNavController()
        NavHost(navController, startDestination = "Home") {
            composable("Home") { Home(navController) }
        }
//        OrdersView()
    }
}
@Composable
@Preview
fun Home(navController: NavHostController)
{
    val scope= rememberCoroutineScope()

    Button(onClick = {
        Bluetooth(scope)
//        flowCollector.
    }) {
        Text("New Orders")
    }
}

private fun Bluetooth(scope: CoroutineScope) {
    val scanner = Scanner {
        filters = listOf(
            Filter.Service(uuidFrom("0000acf0-0000-1000-8000-00805f9b34fb"))
        )
    }
    scope.launch {
        val advertisement = scanner.advertisements.onEach { advertisement ->
            println(advertisement)
        }.first()
        val peripheral = scope.peripheral(advertisement)
        peripheral.connect()
        val service = peripheral.services?.first()
        service?.characteristics?.forEach {
            println(it)
        }
//            peripheral.disconnect()
    }
}
