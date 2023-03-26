package com.example.mytodoapp.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import com.example.mytodoapp.R
import com.example.mytodoapp.entities.AppContext
import com.example.mytodoapp.ui.theme.Shapes
import kotlinx.coroutines.launch

@Composable
fun AlertMessageDialog(
    title: String,
    caption: String,
    isDialogOpen: MutableState<Boolean>) {

    val coroutineScope = rememberCoroutineScope()
    val snackBarText = stringResource(id = R.string.trash_move_alert)

    AlertDialog(
        backgroundColor = MaterialTheme.colors.primary,
        shape = Shapes.medium,
        onDismissRequest = {
            isDialogOpen.value = false
        },
        title = {
            Text(
                text = title,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colors.primary),
                color = MaterialTheme.colors.secondary,
                style = MaterialTheme.typography.body1,
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Text(
                text = caption,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colors.primary),
                color = MaterialTheme.colors.secondary,
                style = MaterialTheme.typography.body2
            )
        },
        buttons = {
            Row(
                verticalAlignment = Alignment.Bottom,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colors.primary)
                    .padding(5.dp)
            ) {
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1F),
                    contentPadding = PaddingValues(5.dp, 15.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = MaterialTheme.colors.primary
                    ),
                    elevation = null,
                    border = null,
                    onClick = {
                        isDialogOpen.value = false
                        coroutineScope.launch {
                            AppContext.scaffoldState.snackbarHostState.showSnackbar(
                                message = snackBarText,
                                duration = SnackbarDuration.Short
                            )
                        }
                    }
                ) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth(),
                        text = stringResource(id = R.string.yes_button).uppercase(),
                        color = MaterialTheme.colors.secondary,
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
                        .weight(1F),
                    contentPadding = PaddingValues(5.dp, 15.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = MaterialTheme.colors.primary
                    ),
                    elevation = null,
                    border = null,
                    onClick = {
                        isDialogOpen.value = false
                    }
                ) {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth(),
                        text = stringResource(id = R.string.no_button).uppercase(),
                        color = MaterialTheme.colors.secondary,
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
    )
}