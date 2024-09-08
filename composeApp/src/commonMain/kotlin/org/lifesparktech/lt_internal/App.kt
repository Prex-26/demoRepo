package org.lifesparktech.lt_internal

import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme {
        var navController= rememberNavController()
        NavHost(navController, startDestination = "Home") {
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

//    OrdersView()
}
