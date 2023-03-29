package com.example.mytodoapp.components.content.category

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.mytodoapp.entities.db.Category
import com.example.mytodoapp.ui.theme.PrimaryLight
import com.example.mytodoapp.ui.theme.Shapes

@Composable
fun CategoryShortCard(category: Category) {
    val red = category.color.substring((2..3)).toInt(radix = 16)
    val green = category.color.substring((4..5)).toInt(radix = 16)
    val blue = category.color.substring((6..7)).toInt(radix = 16)
    val alpha = category.color.substring((0..1)).toInt(radix = 16)

    val cardColor = Color(red, green, blue, alpha)

    Button(
        modifier = Modifier
            .padding(5.dp, 3.dp),
        shape = Shapes.large,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = cardColor
        ),
        onClick = { /*TODO*/ }
    ) {
        Text(
            text = category.name,
            modifier = Modifier
                .fillMaxWidth()
                .background(cardColor)
                .padding(1.dp),
            color = PrimaryLight,
            style = MaterialTheme.typography.caption,
            textAlign = TextAlign.Center
        )
    }
}