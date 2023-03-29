package com.example.mytodoapp.components.content

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
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
import com.example.mytodoapp.ui.theme.Shapes

@Composable
fun AlertMessageDialog(
    dialogData: Pair<String, String>,
    isDialogOpen: MutableState<Boolean>,
    isSnackBarActive: MutableState<Boolean>) {

    AlertDialog(
        backgroundColor = MaterialTheme.colors.primary,
        shape = Shapes.medium,
        onDismissRequest = {
            isDialogOpen.value = false
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
                        AppContext.categoryViewModel
                            ?.remove(AppContext.selectedItems[0] as Category)
                        isDialogOpen.value = false
                        isSnackBarActive.value = true
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