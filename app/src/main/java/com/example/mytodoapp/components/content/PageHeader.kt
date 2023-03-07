package com.example.mytodoapp.components.content

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.mytodoapp.ui.theme.PrimaryLight
import com.example.mytodoapp.ui.theme.SecondaryLight
import com.example.mytodoapp.ui.theme.openSansFamily

@Composable
fun PageHeader(headerText: String) {

    Text(
        text = headerText,
        modifier = Modifier
            .fillMaxSize()
            .background(PrimaryLight)
            .border(
                border = BorderStroke(1.dp, SecondaryLight)
            )
            .padding(0.dp, 10.dp),
        color = SecondaryLight,
        fontFamily = openSansFamily,
        fontWeight = FontWeight.Light,
        fontSize = 24.sp,
        textAlign = TextAlign.Center
    )
}