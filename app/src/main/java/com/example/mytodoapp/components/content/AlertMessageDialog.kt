package com.example.mytodoapp.components.content

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
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
import com.example.mytodoapp.entities.db.Category
import com.example.mytodoapp.entities.db.Task
import com.example.mytodoapp.ui.theme.Shapes

@Composable
fun AlertMessageDialog(
    dialogData: Pair<String, String>,
    isDeleteActive: Boolean
) {

    AlertDialog(
        backgroundColor = MaterialTheme.colors.primary,
        shape = Shapes.medium,
        onDismissRequest = {
            AppContext.contentViewModel?.setDialogState(false)
        },
        title = {
            Text(
                text = dialogData.first,
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
                text = dialogData.second,
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
                        AppContext.selectedItems.forEach { item ->
                            if (isDeleteActive) {
                                AppContext.taskViewModel?.remove(item as Task)
                            }
                            else {
                                AppContext.categoryViewModel?.remove(item as Category)
                            }
                        }

                        AppContext.selectedItems.clear()
                        AppContext.contentViewModel?.setDialogState(false)
                        AppContext.contentViewModel?.setDeleteState(false)
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
                        if (!isDeleteActive) {
                            AppContext.selectedItems.clear()
                        }
                        AppContext.contentViewModel?.setDialogState(false)
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