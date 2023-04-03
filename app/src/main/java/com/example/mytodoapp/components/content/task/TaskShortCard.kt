package com.example.mytodoapp.components.content.task

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.mytodoapp.activities.TaskActivity
import com.example.mytodoapp.constants.IntentKeys
import com.example.mytodoapp.entities.db.Category
import com.example.mytodoapp.entities.db.Task
import com.example.mytodoapp.services.ColorConverter
import com.example.mytodoapp.ui.theme.PrimaryLight
import com.example.mytodoapp.ui.theme.Shapes
import kotlinx.serialization.json.Json
import kotlinx.serialization.encodeToString

@Composable
fun TaskShortCard(
    task: Task,
    colorConverter: ColorConverter,
    categories: List<Category>,
    context: Context
) {
    val foundCategory = categories.find { it.uId == task.category }
    val (red, green, blue, alpha) = colorConverter.getRgba(foundCategory!!.color)

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                onClick = {
                    val jsonTask = Json.encodeToString(task)
                    val jsonCategory = Json.encodeToString(foundCategory)

                    val intent = Intent(context, TaskActivity::class.java)
                    intent.putExtra(IntentKeys.CURRENT_TASK.stringValue, jsonTask)
                    intent.putExtra(IntentKeys.CURRENT_CATEGORY.stringValue, jsonCategory)

                    context.startActivity(intent)
                }
            )
            .padding(5.dp, 10.dp)
            .border(
                border = BorderStroke(
                    width = 1.dp,
                    color = MaterialTheme.colors.primary
                )
            )
            .background(
                brush = Brush.horizontalGradient(
                    0f to Color(red, green, blue, alpha),
                    2F to MaterialTheme.colors.primary
                ),
                shape = Shapes.medium
            )
            .padding(30.dp)
    ) {
        Text(
            text = task.title,
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp),
            color = PrimaryLight,
            style = MaterialTheme.typography.subtitle1,
            textAlign = TextAlign.Left
        )
    }
}