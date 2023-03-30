package com.example.mytodoapp.components.content.category

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import com.example.mytodoapp.R
import com.example.mytodoapp.entities.AppContext
import com.example.mytodoapp.entities.db.Category
import com.example.mytodoapp.services.ColorConverter
import com.example.mytodoapp.ui.theme.Error
import com.example.mytodoapp.ui.theme.SecondaryDark
import com.example.mytodoapp.ui.theme.SecondaryLight
import com.example.mytodoapp.ui.theme.Shapes
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AddCategory(
    parentRadius: Dp,
    lastCategoryIndex: Int) {
    val categoryCardBgColor = MaterialTheme.colors.primaryVariant.value

    var isErrorInput by remember {
        mutableStateOf(false)
    }

    val coroutineScope = rememberCoroutineScope()
    var categoryName by remember {
        mutableStateOf(TextFieldValue(""))
    }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .background(MaterialTheme.colors.primary)
    ) {
        Text(
            text = stringResource(id = R.string.add_category),
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp, 0.dp, 0.dp, 10.dp)
                .background(
                    color = MaterialTheme.colors.primaryVariant,
                    shape = RoundedCornerShape(
                        topStart = parentRadius,
                        topEnd = parentRadius
                    )
                )
                .padding(10.dp),
            color = MaterialTheme.colors.secondary,
            style = MaterialTheme.typography.subtitle1,
            textAlign = TextAlign.Center
        )

        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp, 0.dp, 20.dp, 100.dp),
            value = categoryName,
            label = {
                Text(
                    text = stringResource(
                        id = R.string.name_placeholder
                    )
                )
            },
            onValueChange = {
                categoryName = it
            },
            textStyle = MaterialTheme.typography.body2,
            singleLine = true,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                textColor = MaterialTheme.colors.secondary,
                cursorColor = MaterialTheme.colors.secondaryVariant,
                backgroundColor = MaterialTheme.colors.primary,
                errorBorderColor = Error,
                errorLabelColor = Error,
                errorCursorColor = MaterialTheme.colors.secondaryVariant,
                unfocusedBorderColor = MaterialTheme.colors.secondaryVariant,
                unfocusedLabelColor = MaterialTheme.colors.secondaryVariant,
                focusedBorderColor = MaterialTheme.colors.secondary,
                focusedLabelColor = MaterialTheme.colors.secondary,
                placeholderColor = MaterialTheme.colors.onSecondary,
                disabledPlaceholderColor = MaterialTheme.colors.secondary
            ),
            isError = isErrorInput
        )

        Button(
            modifier = Modifier
                .padding(10.dp, 0.dp, 10.dp, 30.dp)
                .fillMaxWidth()
                .padding(15.dp, 10.dp),
            contentPadding = PaddingValues(5.dp, 15.dp),
            shape = Shapes.large,
            colors = ButtonDefaults.buttonColors(
                backgroundColor = SecondaryLight
            ),
            onClick = {
                isErrorInput = categoryName.text.isEmpty()

                if (!isErrorInput) {
                    coroutineScope.launch {
                        val colorConverter = ColorConverter(16)

                        var isBackground = true
                        var categoryColor = ""

                        while (isBackground) {
                            categoryColor = colorConverter.getStringColor()
                            isBackground = categoryCardBgColor ==
                                    colorConverter.convertWithRadix(categoryColor)
                        }

                        AppContext.categoryViewModel?.add(
                            Category(
                                name = categoryName.text.trim(),
                                color = categoryColor
                            )
                        )

                        categoryName = TextFieldValue("")
                        AppContext.sheetState.hide()
                        AppContext.categoriesListState.scrollToItem(lastCategoryIndex)
                    }
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