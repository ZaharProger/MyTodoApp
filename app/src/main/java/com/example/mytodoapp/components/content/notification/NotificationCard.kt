package com.example.mytodoapp.components.content.notification

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.content.Context
import android.content.Intent
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import com.example.mytodoapp.R
import com.example.mytodoapp.activities.MainActivity
import com.example.mytodoapp.entities.db.Category
import com.example.mytodoapp.entities.db.Task
import com.example.mytodoapp.services.Alarm
import com.example.mytodoapp.services.ColorConverter
import com.example.mytodoapp.ui.theme.PrimaryLight
import com.example.mytodoapp.ui.theme.SecondaryDark
import com.example.mytodoapp.ui.theme.SecondaryLight
import com.example.mytodoapp.ui.theme.Shapes
import com.example.mytodoapp.viewmodels.TaskViewModel
import java.util.*

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun NotificationCard(
    currentTask: Task?,
    currentCategory: Category?,
    context: Context = LocalContext.current,
    taskViewModel: TaskViewModel = TaskViewModel(context)
) {

    val tasks by taskViewModel.tasks.observeAsState(listOf())
    val scrollState = rememberScrollState()

    val getId = { uid: Long ->
        if (uid == 0L) {
            tasks.find { it.title == currentTask?.title && it.data == currentTask.data &&
                    it.category == currentTask.category
            }!!.uId
        }
        else {
            uid
        }
    }

    val colorConverter = ColorConverter(16)
    val (red, green, blue, alpha) = if (currentCategory != null) {
        colorConverter.getRgba(currentCategory.color)
    }
    else {
        arrayOf(0, 0, 0, 0)
    }

    BackHandler(
        enabled = true
    ) {
        currentTask
            ?.apply {
                this.uId = getId(this.uId)
                this.notificationDateTime = null
            }
            ?.let { taskViewModel.update(it) }

        val intent = Intent(context, MainActivity::class.java)
        context.startActivity(intent)
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        backgroundColor = MaterialTheme.colors.primary,
        bottomBar = {
            Row(
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp, 10.dp)
            ) {
                Button(
                    modifier = Modifier
                        .weight(
                            weight = 1F,
                            fill = true
                        )
                        .padding(0.dp, 0.dp, 10.dp, 0.dp),
                    contentPadding = PaddingValues(5.dp, 15.dp),
                    shape = Shapes.large,
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = SecondaryLight
                    ),
                    onClick = {
                        currentTask
                            ?.apply { this.uId = getId(this.uId) }
                            ?.let { taskViewModel.remove(it) }

                        val intent = Intent(context, MainActivity::class.java)
                        context.startActivity(intent)
                    }) {
                    Text(
                        text = stringResource(id = R.string.finish_text).uppercase(),
                        color = SecondaryDark,
                        style = MaterialTheme.typography.caption,
                        textAlign = TextAlign.Center,
                        letterSpacing = TextUnit(
                            value = 1F,
                            TextUnitType.Sp
                        )
                    )
                }

                Button(
                    modifier = Modifier
                        .weight(
                            weight = 1F,
                            fill = true
                        )
                        .padding(10.dp, 0.dp, 0.dp, 0.dp),
                    contentPadding = PaddingValues(5.dp, 15.dp),
                    shape = Shapes.large,
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = SecondaryLight
                    ),
                    onClick = {
                        if (currentTask != null && currentCategory != null) {
                            val fireTime = System.currentTimeMillis() +
                                    AlarmManager.INTERVAL_FIFTEEN_MINUTES

                            currentTask.apply {
                                this.uId = getId(this.uId)
                                this.notificationDateTime = fireTime
                            }

                            taskViewModel.update(currentTask)
                            val alarmManager = Alarm(context)
                            alarmManager.set(
                                currentTask,
                                currentCategory,
                                fireTime
                            )
                        }

                        val intent = Intent(context, MainActivity::class.java)
                        context.startActivity(intent)
                    }) {
                    Text(
                        text = stringResource(id = R.string.notify_text).uppercase(),
                        color = SecondaryDark,
                        style = MaterialTheme.typography.caption,
                        textAlign = TextAlign.Center,
                        letterSpacing = TextUnit(
                            value = 1F,
                            TextUnitType.Sp
                        )
                    )
                }
            }
        }
    ) {
        Column(
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colors.primary)
        ) {
            Column(
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(scrollState)
                    .padding(0.dp, 0.dp, 0.dp, 70.dp)
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(red, green, blue, alpha))
                        .padding(0.dp, 0.dp, 0.dp, 20.dp)
                ) {
                    Text(
                        text = currentTask?.title ?: "",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(0.dp, 0.dp, 0.dp, 5.dp),
                        color = PrimaryLight,
                        style = MaterialTheme.typography.h1,
                        textAlign = TextAlign.Center
                    )

                    Text(
                        text = "Из категории ${currentCategory?.name ?: ""}",
                        modifier = Modifier
                            .fillMaxWidth(),
                        color = PrimaryLight,
                        style = MaterialTheme.typography.caption,
                        textAlign = TextAlign.Center
                    )
                }

                Text(
                    text = currentTask?.data ?: "",
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colors.primary)
                        .padding(5.dp, 10.dp, 5.dp, 0.dp),
                    color = MaterialTheme.colors.secondary,
                    style = MaterialTheme.typography.body2,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}