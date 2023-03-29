package com.example.mytodoapp.components.navbar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.mytodoapp.components.content.category.CategoriesPage
import com.example.mytodoapp.components.content.task.TasksPage
import com.example.mytodoapp.constants.Routes
import com.example.mytodoapp.entities.db.Category

@Composable
fun PageView(
    navController: NavHostController,
    categories: List<Category>) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(MaterialTheme.colors.primary)
    ) {
        NavHost(
            navController = navController,
            startDestination = Routes.TASKS.stringValue
        ) {
            composable(Routes.TASKS.stringValue) {
                TasksPage(categories)
            }
            composable(Routes.CATEGORIES.stringValue) {
                CategoriesPage(categories)
            }
        }
    }
}