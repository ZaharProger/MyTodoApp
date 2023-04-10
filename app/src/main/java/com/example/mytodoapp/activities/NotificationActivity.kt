package com.example.mytodoapp.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import com.example.mytodoapp.components.content.notification.NotificationCard
import com.example.mytodoapp.constants.IntentKeys
import com.example.mytodoapp.entities.db.Category
import com.example.mytodoapp.entities.db.Task
import com.example.mytodoapp.ui.theme.MyTodoAppTheme
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class NotificationActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val taskKey = IntentKeys.CURRENT_TASK.stringValue
        val categoryKey = IntentKeys.CURRENT_CATEGORY.stringValue

        val currentTask = intent.getStringExtra(taskKey)?.let {
            Json.decodeFromString<Task>(it)
        }
        val currentCategory = intent.getStringExtra(categoryKey)?.let {
            Json.decodeFromString<Category>(it)
        }

        setContent {
            MyTodoAppTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colors.primary)
                ) {
                    NotificationCard(currentTask, currentCategory)
                }
            }
        }
    }
}