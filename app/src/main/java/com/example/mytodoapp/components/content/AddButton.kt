package com.example.mytodoapp.components.content

import android.content.Intent
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.mytodoapp.R
import com.example.mytodoapp.activities.TaskActivity
import com.example.mytodoapp.constants.Routes
import com.example.mytodoapp.entities.AppContext
import com.example.mytodoapp.ui.theme.SecondaryLight
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun AddButton(
    isFabActive: Boolean = true,
    hasCaption: Boolean = true
) {
    val context = LocalContext.current

    val coroutineScope = rememberCoroutineScope()
    var buttonModifier = Modifier
        .background(Color.Transparent)

    if (!isFabActive) {
        buttonModifier = buttonModifier.fillMaxSize()
    }

    Column(
        horizontalAlignment = if (isFabActive) Alignment.End else Alignment.CenterHorizontally,
        verticalArrangement = if (isFabActive) Arrangement.Bottom else Arrangement.Center,
        modifier = buttonModifier
    ) {
        IconButton(
            modifier = Modifier
                .clip(CircleShape)
                .background(SecondaryLight)
                .padding(5.dp),
            onClick = {
                coroutineScope.launch {
                    when (AppContext.currentRoute) {
                        Routes.CATEGORIES.stringValue -> AppContext.sheetState.show()
                        Routes.TASKS.stringValue -> {
                            val intent = Intent(context, TaskActivity::class.java)
                            context.startActivity(intent)
                        }
                    }
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

        if (hasCaption) {
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
}