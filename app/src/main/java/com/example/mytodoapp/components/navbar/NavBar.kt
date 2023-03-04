package com.example.mytodoapp.components.navbar

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.mytodoapp.entities.NavBarItem
import com.example.mytodoapp.ui.theme.InactiveLight
import com.example.mytodoapp.ui.theme.PrimaryLight
import com.example.mytodoapp.ui.theme.SecondaryLight

@Composable
fun NavBar(navController: NavHostController, items: List<NavBarItem>) {
    BottomNavigation(
        modifier = Modifier
            .fillMaxWidth()
            .background(PrimaryLight)
            .border(
                border = BorderStroke(1.dp, SecondaryLight)
            ),
    ) {
        val backStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = backStackEntry?.destination?.route

        items.forEach { item ->
            BottomNavigationItem(
                icon = { Icon(
                    imageVector = ImageVector.vectorResource(id = item.icon),
                    contentDescription = item.route.stringValue) },
                selected = currentRoute == item.route.stringValue,
                selectedContentColor = SecondaryLight,
                unselectedContentColor = InactiveLight,
                onClick = {
                    if (currentRoute != item.route.stringValue) {
                        navController.navigate(item.route.stringValue)
                    }
                }
            )
        }
    }
}