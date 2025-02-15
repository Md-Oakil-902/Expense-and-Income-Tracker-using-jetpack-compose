package com.oakil.incomeandexpensetracker

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.oakil.incomeandexpensetracker.feature.add_expense.AddExpense
import com.oakil.incomeandexpensetracker.feature.home.HomeScreen


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