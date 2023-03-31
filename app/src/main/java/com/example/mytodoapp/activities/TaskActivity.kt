package com.example.mytodoapp.activities

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import com.example.mytodoapp.components.content.task.TaskFullCard
import com.example.mytodoapp.constants.IntentKeys
import com.example.mytodoapp.entities.db.Task
import com.example.mytodoapp.ui.theme.MyTodoAppTheme

class TaskActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val intentKey = IntentKeys.CURRENT_TASK.stringValue

        val currentTask = if (intent.hasExtra(intentKey)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                intent.getParcelableExtra(intentKey, Task::class.java)
            } else {
                intent.getParcelableExtra(intentKey)
            }
        }
        else {
            null
        }

        setContent {
            MyTodoAppTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    TaskFullCard(currentTask)
                }
            }
        }
    }
}