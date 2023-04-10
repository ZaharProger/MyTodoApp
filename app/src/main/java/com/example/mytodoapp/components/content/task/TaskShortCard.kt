package com.example.mytodoapp.components.content.task

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.mytodoapp.activities.TaskActivity
import com.example.mytodoapp.constants.IntentKeys
import com.example.mytodoapp.entities.AppContext
import com.example.mytodoapp.entities.db.Category
import com.example.mytodoapp.entities.db.Task
import com.example.mytodoapp.services.ColorConverter
import com.example.mytodoapp.ui.theme.DeleteBackground
import com.example.mytodoapp.ui.theme.PrimaryLight
import com.example.mytodoapp.ui.theme.Shapes
import kotlinx.serialization.json.Json
import kotlinx.serialization.encodeToString

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TaskShortCard(
    modifier: Modifier,
    task: Task,
    isDeleteActive: Boolean,
    colorConverter: ColorConverter,
    categories: List<Category>,
    context: Context
) {
    val foundCategory = categories.find { it.uId == task.category }
    val (red, green, blue, alpha) = colorConverter.getRgba(
        foundCategory?.color ?: "00FFFFFF")

    var inSelectedItems by remember {
        mutableStateOf(AppContext.selectedItems.contains(task))
    }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .combinedClickable (
                onLongClick = {
                    if (!inSelectedItems) {
                        AppContext.contentViewModel?.setDeleteState(true)
                        AppContext.selectedItems.add(task)
                        inSelectedItems = true
                    }
                },
                onClick = {
                    if (isDeleteActive) {
                        if (inSelectedItems) {
                            AppContext.selectedItems.remove(task)
                            inSelectedItems = false

                            if (AppContext.selectedItems.size == 0) {
                                AppContext.contentViewModel?.setDeleteState(false)
                            }
                        }
                        else {
                            AppContext.selectedItems.add(task)
                            inSelectedItems = true
                        }
                    }
                    else {
                        val jsonTask = Json.encodeToString(task)
                        val jsonCategory = Json.encodeToString(foundCategory)

                        val intent = Intent(context, TaskActivity::class.java).apply {
                            putExtra(IntentKeys.CURRENT_TASK.stringValue, jsonTask)
                            putExtra(IntentKeys.CURRENT_CATEGORY.stringValue, jsonCategory)
                        }

                        context.startActivity(intent)
                    }
                }
            )
            .padding(0.dp)
            .background(
                color = if (inSelectedItems)
                    DeleteBackground else MaterialTheme.colors.primary
            )
            .padding(5.dp)
            .border(
                border = BorderStroke(
                    width = 1.dp,
                    color = Color.Transparent
                )
            )
            .background(
                brush = Brush.horizontalGradient(
                    0F to Color(red, green, blue, alpha),
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