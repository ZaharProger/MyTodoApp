package com.example.mytodoapp.components.content

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.mytodoapp.ui.theme.PrimaryLight
import com.example.mytodoapp.ui.theme.SecondaryLight
import com.example.mytodoapp.ui.theme.openSansFamily
import com.example.mytodoapp.viewmodels.ContentViewModel

@Composable
fun SettingsPage(contentViewModel: ContentViewModel) {
    contentViewModel.setTopAppBarHeader("Настройки")

    Text(
        text = "Settings will be here",
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(PrimaryLight),
        color = SecondaryLight,
        fontFamily = openSansFamily,
        fontSize = 16.sp,
        fontWeight = FontWeight.Light
    )
}