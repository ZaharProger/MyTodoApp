package com.example.mytodoapp.components.content.task

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.unit.dp
import com.example.mytodoapp.R
import com.example.mytodoapp.constants.FieldTypes
import com.example.mytodoapp.entities.db.Category
import com.example.mytodoapp.services.ColorConverter
import com.example.mytodoapp.ui.theme.PrimaryLight
import com.example.mytodoapp.ui.theme.Shapes
import com.example.mytodoapp.viewmodels.CategoryViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun TaskFullCardHeader(
    headerText: MutableState<TextFieldValue>,
    selectedCategory: MutableState<Category>,
    correctFields: Map<FieldTypes, Boolean>,
    textFieldColors: TextFieldColors,
    categoryViewModel: CategoryViewModel = CategoryViewModel(LocalContext.current)
) {
    val colorConverter = ColorConverter(16)
    val categories by categoryViewModel.categories.observeAsState(listOf())

    val isExpanded = remember {
        mutableStateOf(false)
    }

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
            value = headerText.value.text,
            onValueChange = {
                headerText.value = TextFieldValue(it)
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
            modifier = Modifier
                .padding(0.dp, 0.dp, 0.dp, 15.dp),
            expanded = isExpanded.value,
            onExpandedChange = {
                isExpanded.value = !isExpanded.value
            }
        ) {
            val reversedCategories = categories.reversed()
            val (r, g, b, a) = colorConverter.getRgba(selectedCategory.value.color)

            TextField(
                readOnly = true,
                value = selectedCategory.value.name,
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
                        expanded = isExpanded.value
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
                expanded = isExpanded.value,
                onDismissRequest = {
                    isExpanded.value = false
                }
            ) {
                reversedCategories.forEach { category ->
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
                                selectedCategory.value = category
                                isExpanded.value = false
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
}