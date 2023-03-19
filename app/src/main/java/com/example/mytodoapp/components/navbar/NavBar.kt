package com.example.mytodoapp.components.navbar

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.mytodoapp.entities.ui.NavBarItem

@Composable
fun NavBar(
    navController: NavHostController,
    items: List<NavBarItem>) {

    BottomNavigation(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.primary)
            .border(
                border = BorderStroke(1.dp, MaterialTheme.colors.secondary)
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
                selectedContentColor = MaterialTheme.colors.secondary,
                unselectedContentColor = MaterialTheme.colors.secondaryVariant,
                onClick = {
                    if (currentRoute != item.route.stringValue) {
                        navController.navigate(item.route.stringValue)
                    }
                }
            )
        }
    }
}