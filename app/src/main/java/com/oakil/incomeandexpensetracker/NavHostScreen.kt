package com.oakil.incomeandexpensetracker

import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.oakil.incomeandexpensetracker.feature.add_expense.AddExpense
import com.oakil.incomeandexpensetracker.feature.home.HomeScreen
import com.oakil.incomeandexpensetracker.feature.states.StatesScreen
import com.oakil.incomeandexpensetracker.ui.theme.Zinc
import kotlinx.coroutines.selects.select


@Composable
fun NavHostScreen() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "/home") {
        composable(route = "/home") {
            HomeScreen(navController)
        }
        composable(route = "/add") {
            AddExpense(navController)
        }
        composable(route = "/stats") {
            StatesScreen(navController)
        }
    }
}


data class NavItem(
    val route: String,
    val icon: Int
)


@Composable
fun NavigationBottomBar(
    navController: NavController,
    items: List<NavItem>
) {

    //bottom navigation bar
    val navBackStackEntry = navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry.value?.destination?.route

    BottomAppBar {
        items.forEach { item ->
            NavigationBarItem(selected = currentRoute == item.route, onClick = {
                navController.navigate(item.route) {
                    popUpTo(navController.graph.startDestinationId)
                    launchSingleTop = true
                    restoreState = true
                }
            },
                icon = {
                Icon(painter = painterResource(id = item.icon), contentDescription = null)
            },
                alwaysShowLabel = false,
                colors = NavigationBarItemDefaults.colors(
                    selectedTextColor = Zinc,
                    selectedIconColor = Zinc
                )
                )
        }

    }

}

