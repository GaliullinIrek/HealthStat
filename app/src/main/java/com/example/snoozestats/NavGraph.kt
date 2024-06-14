package com.example.snoozestats

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.snoozestats.screens.HomeScreen
import com.example.snoozestats.screens.ProfileScreen
import com.example.snoozestats.screens.RecumScreen
import com.example.snoozestats.screens.Screen
import com.example.snoozestats.screens.StatScreen

@Composable
fun NavGraph(navController: NavHostController, context: Context) {
    NavHost(navController = navController, startDestination = Screen.Home.route) {
        composable(Screen.Home.route) { HomeScreen() }
        composable(Screen.Stat.route) { StatScreen() }
        composable(Screen.Recum.route) { RecumScreen() }
        composable(Screen.Profile.route) { ProfileScreen(context = context) }
    }
}
