package com.example.mytodoapp.components.content.task

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.mytodoapp.constants.RequestKeys
import com.example.mytodoapp.entities.AppContext
import com.example.mytodoapp.entities.db.Language
import com.example.mytodoapp.ui.theme.Shapes
import com.example.mytodoapp.viewmodels.LanguageViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun LanguageCard(
    modifier: Modifier,
    language: Language,
    selectedLanguage: MutableState<Long>,
    languageViewModel: LanguageViewModel,
    dataText: MutableState<TextFieldValue>,
    translating: MutableState<Boolean>
) {
    val coroutineScope = rememberCoroutineScope()

    Button(
        modifier = modifier
            .padding(5.dp),
        shape = Shapes.small,
        colors = ButtonDefaults.buttonColors(
            backgroundColor = MaterialTheme.colors.primaryVariant
        ),
        onClick = {
            selectedLanguage.value = if (selectedLanguage.value == language.uId) {
                dataText.value = TextFieldValue(AppContext.prevTaskData)
                AppContext.prevTaskData = ""
                0L
            }
            else {
                AppContext.prevTaskData = dataText.value.text
                translating.value = true
                dataText.value = TextFieldValue("")

                coroutineScope.launch(Dispatchers.IO) {
                    val translatedText = withContext(Dispatchers.IO) {
                        languageViewModel.translate(
                            mapOf(
                                RequestKeys.SOURCE_LANGUAGE.stringValue to "auto",
                                RequestKeys.TARGET_LANGUAGE.stringValue to language.code,
                                RequestKeys.TEXT.stringValue to AppContext.prevTaskData
                            )
                        )
                    }

                    translating.value = false
                    dataText.value = TextFieldValue(translatedText)
                }

                language.uId
            }
        }
    ) {
        Text(
            text = language.name,
            modifier = Modifier
                .fillMaxWidth()
                .padding(1.dp),
            color = MaterialTheme.colors.secondary,
            style = MaterialTheme.typography.caption,
            textAlign = TextAlign.Center
        )
    }
}