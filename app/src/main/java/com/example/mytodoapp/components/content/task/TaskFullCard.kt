package com.example.mytodoapp.components.content.task

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import com.example.mytodoapp.constants.FieldTypes
import com.example.mytodoapp.entities.db.Category
import com.example.mytodoapp.entities.db.Task
import com.example.mytodoapp.ui.theme.*

@Composable
fun TaskFullCard(
    currentTask: Task?,
    currentCategory: Category?
) {

    val headerText = remember {
        mutableStateOf(TextFieldValue(currentTask?.title ?: ""))
    }
    val correctFields = remember {
        mutableStateOf(
            mapOf(
                FieldTypes.TASK_HEADER to true,
                FieldTypes.TASK_CATEGORY to true,
                FieldTypes.TASK_DATA to true,
                FieldTypes.TASK_NOTIFICATION to true
            )
        )
    }
    val selectedCategory = remember {
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
            TaskFullCardHeader(
                headerText,
                selectedCategory,
                correctFields.value,
                textFieldColors
            )

            TaskFullCardContent(
                headerText,
                correctFields,
                textFieldColors,
                selectedCategory,
                currentTask
            )
        }
    }
}