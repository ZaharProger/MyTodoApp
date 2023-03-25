package com.example.mytodoapp.components.content.category

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.mytodoapp.R
import com.example.mytodoapp.entities.db.Category
import com.example.mytodoapp.ui.theme.Shapes

@Composable
fun CategoryCard(category: Category) {
    val red = category.color.substring((2..3)).toInt(radix = 16)
    val green = category.color.substring((4..5)).toInt(radix = 16)
    val blue = category.color.substring((6..7)).toInt(radix = 16)
    val alpha = category.color.substring((0..1)).toInt(radix = 16)

    Column(
        horizontalAlignment = CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .background(
                color = MaterialTheme.colors.primaryVariant,
                shape = Shapes.medium
            )
            .border(
                border = BorderStroke(1.dp, MaterialTheme.colors.secondary),
                shape = Shapes.medium
            )
            .padding(15.dp)
    ) {
        Image(
            imageVector = ImageVector.vectorResource(
                id = R.drawable.ic_bookmark
            ),
            contentDescription = "",
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp, 5.dp, 0.dp, 0.dp),
            colorFilter = ColorFilter.tint(Color(red, green, blue, alpha))
        )

        Text(
            text = category.name,
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colors.primaryVariant),
            color = Color(red, green, blue, alpha),
            style = MaterialTheme.typography.body1,
            textAlign = TextAlign.Center
        )
    }
}