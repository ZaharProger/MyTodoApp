package com.example.mytodoapp.entities.ui

import androidx.annotation.StringRes
import com.example.mytodoapp.constants.Routes

data class NavBarItem(val route: Routes, @StringRes val caption: Int, val icon: Int)
