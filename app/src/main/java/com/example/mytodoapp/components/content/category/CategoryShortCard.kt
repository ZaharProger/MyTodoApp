package com.example.mytodoapp.components.content.category

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.mytodoapp.entities.db.Category
import com.example.mytodoapp.services.ColorConverter
import com.example.mytodoapp.ui.theme.PrimaryLight
import com.example.mytodoapp.ui.theme.Shapes

@Composable
fun CategoryShortCard(
    category: Category,
    colorConverter: ColorConverter,
    currentCategory: MutableState<Long>
) {

    val (red, green, blue, alpha) = colorConverter.getRgba(category.color)
    val cardColor = Color(red, green, blue, alpha)

    Button(
        modifier = Modifier
            .padding(5.dp, 3.dp),
        shape = Shapes.large,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = cardColor
        ),
        onClick = {
            currentCategory.value = if (currentCategory.value == category.uId) -1L
                else category.uId
        }
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