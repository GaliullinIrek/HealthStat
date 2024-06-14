package com.example.snoozestats.screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Newspaper
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.rounded.BarChart
import androidx.compose.ui.graphics.vector.ImageVector

sealed class Screen(val route: String, val label: String, val icon: ImageVector) {
    object Home : Screen("home","Главная", Icons.Default.Home)
    object Recum : Screen("recum", "Лента", Icons.Default.Newspaper)
    object Stat : Screen("stat", "Статистика", Icons.Rounded.BarChart)
    object Profile : Screen("setting", "Настройки", Icons.Default.Settings)
}