package org.lifesparktech.lt_internal

import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.navigation.NavHostController
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme {
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

}
