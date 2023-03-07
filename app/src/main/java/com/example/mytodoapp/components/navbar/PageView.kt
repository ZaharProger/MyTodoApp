package com.example.mytodoapp.components.navbar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.mytodoapp.components.content.CategoriesPage
import com.example.mytodoapp.components.content.SettingsPage
import com.example.mytodoapp.components.content.TasksPage
import com.example.mytodoapp.constants.Routes
import com.example.mytodoapp.ui.theme.PrimaryLight
import com.example.mytodoapp.viewmodels.ContentViewModel

@Composable
fun PageView(navController: NavHostController, contentViewModel: ContentViewModel) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(PrimaryLight)
    ) {
        NavHost(
            navController = navController,
            startDestination = Routes.TASKS.stringValue
        ) {
            composable(Routes.TASKS.stringValue) {
                TasksPage(contentViewModel)
            }
            composable(Routes.CATEGORIES.stringValue) {
                CategoriesPage(contentViewModel)
            }
            composable(Routes.SETTINGS.stringValue) {
                SettingsPage(contentViewModel)
            }
        }
    }
}