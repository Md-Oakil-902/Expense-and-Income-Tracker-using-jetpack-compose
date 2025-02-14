package com.oakil.incomeandexpensetracker

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController


@Composable
fun NavHostScreen() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "/home"){
        composable(route = "/home") {
            HomeScreen(navController)
        }
        composable(route = "/add") {
            AddExpense(navController)
        }
    }




}