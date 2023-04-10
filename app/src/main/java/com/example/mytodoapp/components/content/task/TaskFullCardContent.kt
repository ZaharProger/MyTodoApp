package com.example.mytodoapp.components.content.task

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import com.example.mytodoapp.R
import com.example.mytodoapp.activities.MainActivity
import com.example.mytodoapp.constants.FieldTypes
import com.example.mytodoapp.entities.db.Category
import com.example.mytodoapp.entities.db.Task
import com.example.mytodoapp.entities.ui.ValidationCase
import com.example.mytodoapp.services.Alarm
import com.example.mytodoapp.services.Validator
import com.example.mytodoapp.ui.theme.SecondaryDark
import com.example.mytodoapp.ui.theme.SecondaryLight
import com.example.mytodoapp.ui.theme.Shapes
import com.example.mytodoapp.viewmodels.TaskViewModel
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun TaskFullCardContent(
    headerText: MutableState<TextFieldValue>,
    correctFields: MutableState<Map<FieldTypes, Boolean>>,
    textFieldColors: TextFieldColors,
    selectedCategory: MutableState<Category>,
    currentTask: Task?,
    taskViewModel: TaskViewModel = TaskViewModel(LocalContext.current)
) {
    val notificationPlaceholder = stringResource(id = R.string.notification_placeholder)

    val dataText = remember {
        mutableStateOf(TextFieldValue(currentTask?.data ?: ""))
    }
    val notificationDateTime = remember {
        mutableStateOf(notificationPlaceholder)
    }
    val allowNotification = remember {
        mutableStateOf(false)
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.primary)
            .padding(10.dp, 0.dp)
    ) {
        val context = LocalContext.current

        OutlinedTextField(
            modifier = Modifier
                .fillMaxSize()
                .padding(0.dp, 0.dp, 0.dp, 15.dp),
            value = dataText.value.text,
            onValueChange = {
                dataText.value = TextFieldValue(it)
            },
            isError = !correctFields.value[FieldTypes.TASK_DATA]!!,
            singleLine = false,
            minLines = 30,
            colors = textFieldColors,
            textStyle = MaterialTheme.typography.body2,
            placeholder = {
                Text(
                    text = stringResource(
                        id = R.string.task_data_placeholder
                    )
                )
            }
        )

        TaskFullCardNotification(
            context,
            allowNotification,
            notificationDateTime,
            notificationPlaceholder,
            correctFields
        )

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp, 10.dp),
            contentPadding = PaddingValues(5.dp, 15.dp),
            shape = Shapes.large,
            colors = ButtonDefaults.buttonColors(
                backgroundColor = SecondaryLight
            ),
            onClick = {
                val pattern = "[\\s]*"
                val validationCases = mutableMapOf(
                    FieldTypes.TASK_HEADER to
                            ValidationCase(headerText.value.text, pattern),
                    FieldTypes.TASK_DATA to
                            ValidationCase(dataText.value.text, pattern),
                    FieldTypes.TASK_CATEGORY to
                            ValidationCase(selectedCategory.value.name, pattern),
                )
                if (allowNotification.value) {
                    validationCases[FieldTypes.TASK_NOTIFICATION] = ValidationCase(
                        if (notificationDateTime.value == notificationPlaceholder)
                            ""
                        else
                            notificationDateTime.value,
                        pattern
                    )
                }

                val validationResult = Validator.validate(validationCases)

                correctFields.value = validationResult
                if (!correctFields.value.containsValue(false)) {
                    if (currentTask == null) {
                        taskViewModel.add(
                            Task(
                                title = headerText.value.text.trim(),
                                data = dataText.value.text.trim(),
                                category = selectedCategory.value.uId
                            )
                        )
                    }
                    else {
                        currentTask.title = headerText.value.text.trim()
                        currentTask.data = dataText.value.text.trim()
                        currentTask.category = selectedCategory.value.uId
                        taskViewModel.update(currentTask)
                    }

                    if (allowNotification.value) {
                        val fireTime = SimpleDateFormat(
                            "dd.MM.yyyy HH:mm",
                            Locale.ROOT
                        ).parse(notificationDateTime.value)!!.time

                        val alarm = Alarm(context)
                        alarm.set(
                            currentTask ?: Task(
                                title = headerText.value.text.trim(),
                                data = dataText.value.text.trim(),
                                category = selectedCategory.value.uId
                            ),
                            selectedCategory.value,
                            fireTime
                        )
                    }

                    val intent = Intent(context, MainActivity::class.java)
                    context.startActivity(intent)
                }
            }) {

            Text(
                text = stringResource(
                    id = if (currentTask == null)
                        R.string.add_text
                    else
                        R.string.edit_text
                ).uppercase(),
                color = SecondaryDark,
                style = MaterialTheme.typography.subtitle2,
                textAlign = TextAlign.Center,
                letterSpacing = TextUnit(
                    value = 1F,
                    TextUnitType.Sp
                )
            )
        }

    }
}