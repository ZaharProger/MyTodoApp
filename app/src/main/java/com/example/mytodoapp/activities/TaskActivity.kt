package com.example.mytodoapp.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import com.example.mytodoapp.components.content.task.TaskFullCard
import com.example.mytodoapp.ui.theme.MyTodoAppTheme

class TaskActivity: ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MyTodoAppTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    TaskFullCard()
                }
            }
        }
    }
}