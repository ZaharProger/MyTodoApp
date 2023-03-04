package com.example.mytodoapp.components

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.example.mytodoapp.R
import com.example.mytodoapp.components.navbar.NavBar
import com.example.mytodoapp.components.navbar.PageView
import com.example.mytodoapp.constants.Routes
import com.example.mytodoapp.entities.NavBarItem

@Composable
fun ContentWrap() {
    val navController = rememberNavController()
    val navItems = listOf(
        NavBarItem(Routes.TASKS, R.string.tasks_route, R.drawable.ic_tasks),
        NavBarItem(Routes.CATEGORIES, R.string.categories_route, R.drawable.ic_categories),
        NavBarItem(Routes.SETTINGS, R.string.settings_route, R.drawable.ic_settings)
    )

    Scaffold(
        bottomBar = { NavBar(navController, navItems) }
    ) {
        PageView(navController)
    }
}