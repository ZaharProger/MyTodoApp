package com.example.mytodoapp.components.content.task

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.widget.DatePicker
import android.widget.TimePicker
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import com.example.mytodoapp.R
import com.example.mytodoapp.constants.FieldTypes
import com.example.mytodoapp.services.DateTimeConverter
import com.example.mytodoapp.ui.theme.Error
import com.example.mytodoapp.ui.theme.Shapes

@Composable
fun TaskFullCardNotification(
    context: Context,
    allowNotification: MutableState<Boolean>,
    notificationDateTime: MutableState<String>,
    notificationPlaceholder: String,
    correctFields: MutableState<Map<FieldTypes, Boolean>>,
) {

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start,
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp, 0.dp, 10.dp, 10.dp)
    ) {
        Checkbox(
            modifier = Modifier
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colors.secondary,
                    shape = Shapes.small
                ),
            checked = allowNotification.value,
            onCheckedChange = {
                allowNotification.value = it
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
            .padding(10.dp, 0.dp, 10.dp, 20.dp),
        enabled = allowNotification.value,
        contentPadding = PaddingValues(5.dp),
        shape = Shapes.small,
        border = BorderStroke(
            width = 1.dp,
            color = if (allowNotification.value) MaterialTheme.colors.secondary
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
                    notificationDateTime.value = dateTimeConverter
                        .convertToString(date, ".")

                    val timePicker = TimePickerDialog(
                        context,
                        { _: TimePicker, hour: Int, minute: Int ->
                            val time = arrayOf(hour, minute)
                            notificationDateTime.value = "${notificationDateTime.value} " +
                                    dateTimeConverter.convertToString(time, ":")
                        },
                        currentHour,
                        currentMinute,
                        true
                    )

                    timePicker.apply {
                        this.setOnCancelListener {
                            notificationDateTime.value = notificationPlaceholder
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
                    notificationDateTime.value = notificationPlaceholder
                }
            }
            datePicker.show()
        }
    ) {
        Text(
            text = notificationDateTime.value,
            color = if (correctFields.value[FieldTypes.TASK_NOTIFICATION] == false) {
                if (allowNotification.value)
                    Error
                else
                    MaterialTheme.colors.onSecondary
            }
            else {
                if (notificationDateTime.value == notificationPlaceholder ||
                    !allowNotification.value)

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
}