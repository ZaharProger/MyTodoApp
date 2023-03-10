package com.example.mytodoapp.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomAppBar
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.example.mytodoapp.R
import com.example.mytodoapp.components.content.PageHeader
import com.example.mytodoapp.components.navbar.NavBar
import com.example.mytodoapp.components.navbar.PageView
import com.example.mytodoapp.constants.Routes
import com.example.mytodoapp.entities.NavBarItem
import com.example.mytodoapp.ui.theme.PrimaryLight
import com.example.mytodoapp.viewmodels.ContentViewModel

@Composable
fun ContentWrap(contentViewModel: ContentViewModel = ContentViewModel()) {
    val navController = rememberNavController()
    val navItems = listOf(
        NavBarItem(Routes.TASKS, R.string.tasks_route, R.drawable.ic_tasks),
        NavBarItem(Routes.CATEGORIES, R.string.categories_route, R.drawable.ic_categories),
        NavBarItem(Routes.SETTINGS, R.string.settings_route, R.drawable.ic_settings)
    )

    val headerText by contentViewModel.topAppBarHeader.observeAsState("")

    Scaffold(
        topBar = { TopAppBar(
            backgroundColor = PrimaryLight,
            modifier = Modifier
                .fillMaxWidth(),
            contentPadding = PaddingValues(0.dp)
        ) {
            PageHeader(headerText)
        } },
        bottomBar = { BottomAppBar(
            backgroundColor = PrimaryLight,
            modifier = Modifier
                .fillMaxWidth(),
            contentPadding = PaddingValues(0.dp)
        ) {
            NavBar(navController, navItems)
        } }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
        ) {
            PageView(navController, contentViewModel)
        }
    }
}