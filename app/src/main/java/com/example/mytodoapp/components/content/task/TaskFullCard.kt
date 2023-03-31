package com.example.mytodoapp.components.content.task

import android.content.Intent
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
import com.example.mytodoapp.entities.ui.ValidationCase
import com.example.mytodoapp.services.ColorConverter
import com.example.mytodoapp.services.Validator
import com.example.mytodoapp.ui.theme.*
import com.example.mytodoapp.viewmodels.CategoryViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TaskFullCard(
    categoryViewModel: CategoryViewModel = CategoryViewModel(LocalContext.current)
) {
    val colorConverter = ColorConverter(16)
    val categories by categoryViewModel.categories.observeAsState(listOf())

    var headerText by remember {
        mutableStateOf(TextFieldValue(""))
    }
    var dataText by remember {
        mutableStateOf(TextFieldValue(""))
    }
    var correctFields by remember {
        mutableStateOf(
            mapOf(
                FieldTypes.TASK_HEADER to true,
                FieldTypes.TASK_CATEGORY to true,
                FieldTypes.TASK_DATA to true
            )
        )
    }
    var isExpanded by remember {
        mutableStateOf(false)
    }
    var selectedOptionText by remember {
        mutableStateOf(if (categories.isNotEmpty()) categories[0].name else "")
    }
    var selectedCategoryColor by remember {
        val (red, green, blue, alpha) = if (categories.isNotEmpty()) {
            colorConverter.getRgba(categories[0].color)
        }
        else {
            Array(4) { 0 }
        }

        mutableStateOf(Color(red, green, blue, alpha))
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
                    TextField(
                        readOnly = true,
                        value = selectedOptionText,
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
                            backgroundColor = selectedCategoryColor
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
                                        selectedOptionText = category.name
                                        selectedCategoryColor = categoryColor
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
                        .padding(0.dp, 0.dp, 0.dp, 10.dp),
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

                Button(
                    modifier = Modifier
                        .padding(10.dp, 0.dp)
                        .fillMaxWidth()
                        .padding(15.dp, 10.dp),
                    contentPadding = PaddingValues(5.dp, 15.dp),
                    shape = Shapes.large,
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = SecondaryLight
                    ),
                    onClick = {
                        val pattern = "[\\s]*"
                        val validationResult = Validator.validate(
                            mapOf(
                                FieldTypes.TASK_HEADER to
                                        ValidationCase(headerText.text, pattern),
                                FieldTypes.TASK_DATA to
                                        ValidationCase(dataText.text, pattern),
                                FieldTypes.TASK_CATEGORY to
                                        ValidationCase(selectedOptionText, pattern)
                            )
                        )

                        correctFields = validationResult
                        if (!correctFields.containsValue(false)) {
                            val intent = Intent(context, MainActivity::class.java)
                            context.startActivity(intent)
                        }
                    }) {

                    Text(
                        text = stringResource(id = R.string.add_text).uppercase(),
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