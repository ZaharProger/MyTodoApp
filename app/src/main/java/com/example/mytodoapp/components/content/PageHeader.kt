package com.example.mytodoapp.components.content

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun PageHeader(headerText: String) {

    Text(
        text = headerText,
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.primary)
            .border(
                border = BorderStroke(1.dp, MaterialTheme.colors.secondary)
            )
            .padding(0.dp, 10.dp),
        color = MaterialTheme.colors.secondary,
        style = MaterialTheme.typography.h1,
        textAlign = TextAlign.Center
    )
}