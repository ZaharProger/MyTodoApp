package com.example.mytodoapp.components.content.task

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.widget.DatePicker
import android.widget.TimePicker
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import com.example.mytodoapp.services.*
import com.example.mytodoapp.ui.theme.*
import com.example.mytodoapp.viewmodels.CategoryViewModel
import com.example.mytodoapp.viewmodels.TaskViewModel
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TaskFullCard(
    currentTask: Task?,
    currentCategory: Category?,
    categoryViewModel: CategoryViewModel = CategoryViewModel(LocalContext.current),
    taskViewModel: TaskViewModel = TaskViewModel(LocalContext.current)
) {
    val notificationPlaceholder = stringResource(id = R.string.notification_placeholder)
    val colorConverter = ColorConverter(16)
    val categories by categoryViewModel.categories.observeAsState(listOf())

    var headerText by remember {
        mutableStateOf(TextFieldValue(currentTask?.title ?: ""))
    }
    var dataText by remember {
        mutableStateOf(TextFieldValue(currentTask?.data ?: ""))
    }
    var notificationDateTime by remember {
        mutableStateOf(notificationPlaceholder)
    }
    var allowNotification by remember {
        mutableStateOf(false)
    }
    var correctFields by remember {
        mutableStateOf(
            mapOf(
                FieldTypes.TASK_HEADER to true,
                FieldTypes.TASK_CATEGORY to true,
                FieldTypes.TASK_DATA to true,
                FieldTypes.TASK_NOTIFICATION to true
            )
        )
    }
    var isExpanded by remember {
        mutableStateOf(false)
    }
    var selectedCategory by remember {
        mutableStateOf(currentCategory ?: Category())
    }

    val scrollState = rememberScrollState()

    val textFieldColors = TextFieldDefaults.outlinedTextFieldColors(
        textColor = MaterialTheme.colors.secondary,
        cursorColor = MaterialTheme.colors.secondaryVariant,
        backgroundColor = MaterialTheme.colors.primary,
        errorLabelColor = Error,
        errorBorderColor = Error,
        errorCursorColor = MaterialTheme.colors.secondaryVariant,
        unfocusedLabelColor = MaterialTheme.colors.secondaryVariant,
        focusedLabelColor = MaterialTheme.colors.secondary,
        placeholderColor = MaterialTheme.colors.onSecondary,
        disabledPlaceholderColor = MaterialTheme.colors.secondary,
        focusedBorderColor = MaterialTheme.colors.secondary,
        unfocusedBorderColor = MaterialTheme.colors.secondary
    )

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.primary)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(scrollState)
                .weight(
                    weight = 1F,
                    fill = false
                )
                .background(MaterialTheme.colors.primary)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colors.primary)
                    .padding(10.dp, 10.dp, 10.dp, 25.dp)
            ) {
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(0.dp, 0.dp, 0.dp, 10.dp),
                    value = headerText.text,
                    onValueChange = {
                        headerText = TextFieldValue(it)
                    },
                    singleLine = true,
                    colors = textFieldColors,
                    textStyle = MaterialTheme.typography.h1,
                    isError = !correctFields[FieldTypes.TASK_HEADER]!!,
                    placeholder = {
                        Text(
                            text = stringResource(
                                id = R.string.name_placeholder
                            )
                        )
                    }
                )

                ExposedDropdownMenuBox(
                    expanded = isExpanded,
                    onExpandedChange = {
                        isExpanded = !isExpanded
                    }
                ) {
                    val (r, g, b, a) = colorConverter.getRgba(selectedCategory.color)

                    TextField(
                        readOnly = true,
                        value = selectedCategory.name,
                        onValueChange = { },
                        label = {
                            Text(
                                text = stringResource(
                                    id = R.string.categories_caption
                                )
                            )
                        },
                        isError = !correctFields[FieldTypes.TASK_CATEGORY]!!,
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(
                                expanded = isExpanded
                            )
                        },
                        colors = ExposedDropdownMenuDefaults.textFieldColors(
                            textColor = PrimaryLight,
                            trailingIconColor = MaterialTheme.colors.secondary,
                            focusedLabelColor = MaterialTheme.colors.secondary,
                            backgroundColor = Color(r, g, b, a)
                        ),
                        shape = Shapes.medium
                    )

                    ExposedDropdownMenu(
                        modifier = Modifier
                            .background(
                                color = MaterialTheme.colors.primaryVariant
                            ),
                        expanded = isExpanded,
                        onDismissRequest = {
                            isExpanded = false
                        }
                    ) {
                        categories.forEach { category ->
                            val (red, green, blue, alpha) = colorConverter
                                .getRgba(category.color)
                            val categoryColor = Color(red, green, blue, alpha)

                            DropdownMenuItem(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(
                                        color = MaterialTheme.colors.primaryVariant
                                    ),
                                onClick = {},
                                contentPadding = PaddingValues(3.dp)
                            ) {
                                Button(
                                    onClick = {
                                        selectedCategory = category
                                        isExpanded = false
                                    },
                                    shape = Shapes.small,
                                    colors = ButtonDefaults.buttonColors(
                                        backgroundColor = categoryColor,
                                        contentColor = PrimaryLight
                                    ),
                                    contentPadding = PaddingValues(3.dp)
                                ) {
                                    Text(
                                        text = category.name,
                                        modifier = Modifier
                                            .fillMaxWidth(),
                                        color = PrimaryLight,
                                        style = MaterialTheme.typography.body2,
                                        textAlign = TextAlign.Center
                                    )
                                }
                            }
                        }
                    }
                }
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
                    value = dataText,
                    onValueChange = {
                        dataText = it
                    },
                    isError = !correctFields[FieldTypes.TASK_DATA]!!,
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

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(0.dp, 0.dp, 0.dp, 10.dp)
                ) {
                    Checkbox(
                        modifier = Modifier
                            .border(
                                width = 1.dp,
                                color = MaterialTheme.colors.secondary,
                                shape = Shapes.small
                            ),
                        checked = allowNotification,
                        onCheckedChange = {
                            allowNotification = it
                        },
                        colors = CheckboxDefaults.colors(
                            checkedColor = MaterialTheme.colors.primary,
                            uncheckedColor = MaterialTheme.colors.primary,
                            checkmarkColor = MaterialTheme.colors.secondary
                        )
                    )

                    Text(
                        text = stringResource(
                            id = R.string.notification_label
                        ),
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(5.dp, 0.dp, 0.dp, 0.dp)
                            .background(MaterialTheme.colors.primary),
                        color = MaterialTheme.colors.secondary,
                        style = MaterialTheme.typography.body2,
                        textAlign = TextAlign.Left
                    )
                }

                OutlinedButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(0.dp, 0.dp, 0.dp, 20.dp),
                    enabled = allowNotification,
                    contentPadding = PaddingValues(5.dp),
                    shape = Shapes.small,
                    border = BorderStroke(
                        width = 1.dp,
                        color = if (allowNotification) MaterialTheme.colors.secondary
                            else MaterialTheme.colors.onSecondary
                    ),
                    colors = ButtonDefaults.outlinedButtonColors(
                        backgroundColor = MaterialTheme.colors.primary
                    ),
                    onClick = {
                        val dateTimeConverter = DateTimeConverter()
                        val (currentDay, currentMonth, currentYear, currentHour, currentMinute)
                                = dateTimeConverter.getCurrentDateTime()

                        val datePicker = DatePickerDialog(
                            context,
                            { _: DatePicker, year: Int, month: Int, day: Int ->
                                val date = arrayOf(day, month + 1, year)
                                notificationDateTime = dateTimeConverter
                                    .convertToString(date, ".")

                                val timePicker = TimePickerDialog(
                                    context,
                                    { _: TimePicker, hour: Int, minute: Int ->
                                        val time = arrayOf(hour, minute)
                                        notificationDateTime = "$notificationDateTime " +
                                            dateTimeConverter.convertToString(time, ":")
                                    },
                                    currentHour,
                                    currentMinute,
                                    true
                                )

                                timePicker.apply {
                                    this.setOnCancelListener {
                                        notificationDateTime = notificationPlaceholder
                                    }
                                }
                                timePicker.show()
                            },
                            currentYear,
                            currentMonth,
                            currentDay
                        )

                        datePicker.apply {
                            this.datePicker.minDate = dateTimeConverter.calendar.timeInMillis
                            this.setOnCancelListener {
                                notificationDateTime = notificationPlaceholder
                            }
                        }
                        datePicker.show()
                    }
                ) {
                    Text(
                        text = notificationDateTime,
                        color = if (correctFields[FieldTypes.TASK_NOTIFICATION] == false) {
                            if (allowNotification)
                                Error
                            else
                                MaterialTheme.colors.onSecondary
                        }
                        else {
                            if (notificationDateTime == notificationPlaceholder || !allowNotification)
                                MaterialTheme.colors.onSecondary
                            else
                                MaterialTheme.colors.secondary
                        },
                        style = MaterialTheme.typography.subtitle2,
                        textAlign = TextAlign.Center,
                        letterSpacing = TextUnit(
                            value = 1F,
                            TextUnitType.Sp
                        )
                    )
                }

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
                                    ValidationCase(headerText.text, pattern),
                            FieldTypes.TASK_DATA to
                                    ValidationCase(dataText.text, pattern),
                            FieldTypes.TASK_CATEGORY to
                                    ValidationCase(selectedCategory.name, pattern),
                        )
                        if (allowNotification) {
                            validationCases[FieldTypes.TASK_NOTIFICATION] = ValidationCase(
                                if (notificationDateTime == notificationPlaceholder)
                                    ""
                                else
                                    notificationDateTime,
                                pattern
                            )
                        }

                        val validationResult = Validator.validate(validationCases)

                        correctFields = validationResult
                        if (!correctFields.containsValue(false)) {
                            if (currentTask == null) {
                                taskViewModel.add(Task(
                                    title = headerText.text.trim(),
                                    data = dataText.text.trim(),
                                    category = selectedCategory.uId
                                ))
                            }
                            else {
                                currentTask.title = headerText.text.trim()
                                currentTask.data = dataText.text.trim()
                                currentTask.category = selectedCategory.uId
                                taskViewModel.update(currentTask)
                            }

                            if (allowNotification) {
                                val fireTime = SimpleDateFormat(
                                    "dd.MM.yyyy HH:mm",
                                    Locale.ROOT
                                ).parse(notificationDateTime)!!.time

                                val alarm = Alarm(context)
                                alarm.set(
                                    currentTask ?: Task(
                                        title = headerText.text.trim(),
                                        data = dataText.text.trim(),
                                        category = selectedCategory.uId
                                    ),
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
    }
}