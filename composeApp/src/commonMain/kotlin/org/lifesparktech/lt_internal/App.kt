package org.lifesparktech.lt_internal

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalUriHandler
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme {
        var navController= rememberNavController()
        NavHost(navController, startDestination = "Login") {
            composable("Login") { Login(navController) }
            composable("Home") { Home(navController) }
            composable("Orders") { OrdersView(navController) }
            composable("FlashingScreen/{selectedOrderIds}") { backStackEntry ->
                FlashingScreen(
                    navController,
                    backStackEntry.arguments?.getString("selectedOrderIds") ?: ""
                )
            }
        }
//        OrdersView()
    }
}
@Composable
@Preview
fun Home(navController: NavHostController)
{
    Button(onClick = { navController.navigate("Orders") }) {
        Text("New Orders")
    }
}
@Composable
@Preview
fun Login(navController: NavHostController)
{
    val scope = rememberCoroutineScope()

    val uriHandler = LocalUriHandler.current
    Column {
        Button(onClick = {
            uriHandler.openUri("https://lt-internal.el.r.appspot.com/login")
        }) {
            Text("Login")
        }
        Button(onClick = {
            scope.launch{
                ServerCommunication().getUser()
            }
        }
        ) {
            Text("Validate Login")
        }
    }
}
