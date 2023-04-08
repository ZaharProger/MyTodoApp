package com.example.mytodoapp.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import com.example.mytodoapp.constants.IntentKeys
import com.example.mytodoapp.entities.db.Task
import com.example.mytodoapp.ui.theme.MyTodoAppTheme
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class NotificationActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val taskKey = IntentKeys.CURRENT_TASK.stringValue

        val currentTask = intent.getStringExtra(taskKey)?.let {
            Json.decodeFromString<Task>(it)
        }

        setContent {
            MyTodoAppTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    //TODO: Доделать окно для показа задачи из напоминания
                }
            }
        }
    }
}