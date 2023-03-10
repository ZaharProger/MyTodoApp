package com.example.mytodoapp.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.mytodoapp.R

val openSansFamily = FontFamily(Font(R.font.open_sans_light))
val oswaldFamily = FontFamily(Font(R.font.oswald_light))
val robotoFamily = FontFamily(Font(R.font.roboto_light))
val shantellSansFamily = FontFamily(Font(R.font.shantell_sans_light))

// Set of Material typography styles to start with
val Typography = Typography(
    body1 = TextStyle(
        fontFamily = openSansFamily,
        fontWeight = FontWeight.Light,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.sp
    ),
    body2 = TextStyle(
        fontFamily = openSansFamily,
        fontWeight = FontWeight.Light,
        fontSize = 14.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.sp
    ),
    h1 = TextStyle(
        fontFamily = openSansFamily,
        fontWeight = FontWeight.Light,
        fontSize = 18.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.sp
    )
)