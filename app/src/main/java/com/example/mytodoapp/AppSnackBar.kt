package com.example.mytodoapp

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Snackbar
import androidx.compose.material.SnackbarData
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.mytodoapp.ui.theme.PrimaryLight
import com.example.mytodoapp.ui.theme.SecondaryLight
import com.example.mytodoapp.ui.theme.Shapes

@Composable
fun AppSnackBar(snackbarData: SnackbarData) {
    Snackbar(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp),
        backgroundColor = SecondaryLight,
        contentColor = PrimaryLight,
        shape = Shapes.small,
        elevation = 6.dp
    ){
        Text(text = snackbarData.message,
            modifier = Modifier
                .fillMaxWidth()
                .background(SecondaryLight),
            color = PrimaryLight,
            style = MaterialTheme.typography.body2,
            textAlign = TextAlign.Center
        )
    }
}