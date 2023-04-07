package com.example.mytodoapp.components.content.category

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.mytodoapp.R
import com.example.mytodoapp.constants.Routes
import com.example.mytodoapp.entities.AppContext
import com.example.mytodoapp.entities.db.Category
import com.example.mytodoapp.services.ColorConverter
import com.example.mytodoapp.ui.theme.Shapes

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CategoryFullCard(
    modifier: Modifier,
    navController: NavHostController,
    category: Category,
    colorConverter: ColorConverter,
    currentCategory: MutableState<Long>
) {

    val (red, green, blue, alpha) = colorConverter.getRgba(category.color)
    val cardColor = Color(red, green, blue, alpha)

    Column(
        horizontalAlignment = CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = modifier
            .combinedClickable(
                onLongClick = {
                    AppContext.contentViewModel?.setDialogState(true)
                    AppContext.selectedItems = mutableListOf(category)
                },
                onClick = {
                    currentCategory.value = category.uId
                    navController.navigate(Routes.TASKS.stringValue)
                }
            )
            .background(
                color = MaterialTheme.colors.primaryVariant,
                shape = Shapes.medium
            )
            .border(
                border = BorderStroke(1.dp, MaterialTheme.colors.secondary),
                shape = Shapes.medium
            )
            .padding(15.dp)
    ) {
        Image(
            imageVector = ImageVector.vectorResource(
                id = R.drawable.ic_bookmark
            ),
            contentDescription = "",
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp, 5.dp, 0.dp, 0.dp),
            colorFilter = ColorFilter.tint(cardColor)
        )

        Text(
            text = category.name,
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colors.primaryVariant),
            color = cardColor,
            style = MaterialTheme.typography.body1,
            textAlign = TextAlign.Center
        )
    }
}