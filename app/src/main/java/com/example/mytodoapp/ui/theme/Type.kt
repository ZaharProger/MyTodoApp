package com.example.mytodoapp.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.mytodoapp.R

// Set of Material typography styles to start with
val Typography = Typography(
    body1 = TextStyle(
        fontWeight = FontWeight.Light,
        fontSize = 18.sp,
        fontFamily = FontFamily(Font(R.font.open_sans_light)),
    ),
    body2 = TextStyle(
        fontWeight = FontWeight.Light,
        fontSize = 16.sp,
        fontFamily = FontFamily(Font(R.font.open_sans_light))
    ),
    subtitle1 = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,
        fontFamily = FontFamily(Font(R.font.open_sans_light)),
    ),
    subtitle2 = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
        fontFamily = FontFamily(Font(R.font.open_sans_light)),
    ),
    caption = TextStyle(
        fontWeight = FontWeight.Light,
        fontSize = 12.sp,
        fontFamily = FontFamily(Font(R.font.open_sans_light)),
    ),
    h1 = TextStyle(
        fontWeight = FontWeight.Light,
        fontSize = 24.sp,
        fontFamily = FontFamily(Font(R.font.open_sans_light))
    )
)