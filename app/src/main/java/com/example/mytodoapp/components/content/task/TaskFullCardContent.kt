package com.example.mytodoapp.components.content.task

import android.content.Intent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
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
import com.example.mytodoapp.entities.AppContext
import com.example.mytodoapp.entities.db.Category
import com.example.mytodoapp.entities.db.Task
import com.example.mytodoapp.entities.ui.ValidationCase
import com.example.mytodoapp.services.Alarm
import com.example.mytodoapp.services.DateTimeConverter
import com.example.mytodoapp.services.Validator
import com.example.mytodoapp.ui.theme.SecondaryDark
import com.example.mytodoapp.ui.theme.SecondaryLight
import com.example.mytodoapp.ui.theme.Shapes
import com.example.mytodoapp.viewmodels.LanguageViewModel
import com.example.mytodoapp.viewmodels.TaskViewModel
import kotlinx.coroutines.*

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TaskFullCardContent(
    headerText: MutableState<TextFieldValue>,
    correctFields: MutableState<Map<FieldTypes, Boolean>>,
    textFieldColors: TextFieldColors,
    selectedCategory: MutableState<Category>,
    currentTask: Task?,
    taskViewModel: TaskViewModel = TaskViewModel(LocalContext.current),
    languageViewModel: LanguageViewModel = LanguageViewModel(LocalContext.current)
) {
    val dateTimeConverter = DateTimeConverter()
    val notificationPlaceholder = stringResource(id = R.string.notification_placeholder)

    val languages by languageViewModel.languages.observeAsState(listOf())
    val selectedLanguage = remember {
        mutableStateOf(0L)
    }
    val filteredLanguages = languages
        .filter {
            if (selectedLanguage.value > 0L) {
                it.uId == selectedLanguage.value
            }
            else {
                true
            }
        }
        .sortedBy { it.name.lowercase() }

    val dataText = remember {
        mutableStateOf(TextFieldValue(currentTask?.data ?: ""))
    }
    val notificationDateTime = remember {
        mutableStateOf(if (currentTask != null) {
            if (currentTask.notificationDateTime != null)
                dateTimeConverter.formatToDate(currentTask.notificationDateTime!!)
            else
                notificationPlaceholder
        }
        else
            notificationPlaceholder
        )
    }
    val allowNotification = remember {
        mutableStateOf(false)
    }
    val translating = remember {
        mutableStateOf(false)
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.primary)
            .padding(0.dp)
    ) {
        val context = LocalContext.current
        val languagesState = rememberLazyListState()

       if (filteredLanguages.isEmpty()) {
           Text(
               modifier = Modifier
                   .fillMaxWidth()
                   .padding(10.dp, 0.dp),
               text = stringResource(
                   id = R.string.language_load_label
               ),
               color = MaterialTheme.colors.secondary,
               style = MaterialTheme.typography.body2,
               textAlign = TextAlign.Center,
           )
       }
       else {
           LazyRow(
               state = languagesState,
               modifier = Modifier
                   .fillMaxWidth(),
               contentPadding = PaddingValues(0.dp),
               verticalAlignment = Alignment.CenterVertically
           ) {
               items(
                   filteredLanguages.size,
                   key = { "language_${filteredLanguages[it].uId}" }
               )
               {
                   LanguageCard(
                       Modifier.animateItemPlacement(),
                       filteredLanguages[it],
                       selectedLanguage,
                       languageViewModel,
                       dataText,
                       translating
                   )
               }
           }
       }

        OutlinedTextField(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp, 0.dp, 10.dp, 15.dp),
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
                        id = if (translating.value) R.string.translate_placeholder
                            else R.string.task_data_placeholder
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
                    val taskTitle = headerText.value.text.trim()
                    val taskData = dataText.value.text.trim()
                    val taskCategory = selectedCategory.value.uId
                    val taskLanguage = if (selectedLanguage.value > 0L)
                        selectedLanguage.value else currentTask?.language
                    val fireTime = if (allowNotification.value) {
                        if (notificationDateTime.value == notificationPlaceholder) null
                        else dateTimeConverter.convertToTimeStamp(notificationDateTime.value)
                    }
                    else {
                        null
                    }

                    if (currentTask == null) {
                        taskViewModel.add(
                            Task(
                                title = taskTitle,
                                data = taskData,
                                category = taskCategory,
                                notificationDateTime = fireTime,
                                language = taskLanguage
                            )
                        )
                    }
                    else {
                        currentTask.title = taskTitle
                        currentTask.data = taskData
                        currentTask.category = taskCategory
                        currentTask.notificationDateTime = fireTime
                        currentTask.language = taskLanguage
                        taskViewModel.update(currentTask)
                    }

                    if (allowNotification.value) {
                        val alarm = Alarm(context)
                        alarm.set(
                            currentTask ?: Task(
                                title = taskTitle,
                                data = taskData,
                                category = taskCategory,
                                language = taskLanguage,
                                notificationDateTime = fireTime
                            ),
                            selectedCategory.value,
                            fireTime!!
                        )
                    }

                    AppContext.prevTaskData = ""
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