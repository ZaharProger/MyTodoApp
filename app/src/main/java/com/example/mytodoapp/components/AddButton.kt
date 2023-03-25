package com.example.mytodoapp.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.mytodoapp.R
import com.example.mytodoapp.entities.AppContext
import com.example.mytodoapp.ui.theme.SecondaryLight
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AddButton() {

    val coroutineScope = rememberCoroutineScope()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.primary)
    ) {
        IconButton(
            modifier = Modifier
                .clip(CircleShape)
                .background(SecondaryLight)
                .padding(5.dp),
            onClick = {
                coroutineScope.launch {
                    AppContext.sheetState.show()
                }
            }) {
            Image(
                imageVector = ImageVector.vectorResource(
                    id = R.drawable.ic_add
                ),
                contentDescription = "",
                alignment = Alignment.Center
            )
        }

        Text(
            modifier = Modifier
                .padding(0.dp, 16.dp, 0.dp, 0.dp),
            text = stringResource(id = R.string.add_category),
            color = MaterialTheme.colors.secondary,
            style = MaterialTheme.typography.body1,
            textAlign = TextAlign.Center
        )
    }
}