package com.example.mytodoapp.components.content.category

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.mytodoapp.R
import com.example.mytodoapp.components.content.AddButton
import com.example.mytodoapp.entities.AppContext
import com.example.mytodoapp.entities.db.Category
import com.example.mytodoapp.services.ColorConverter

@Composable
fun CategoriesPage(categories: List<Category>) {
    AppContext.contentViewModel?.setTopAppBarHeader(
        stringResource(
            id = R.string.categories_caption
        )
    )

    if (categories.isNotEmpty()) {
        val colorConverter = ColorConverter(16)

        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 128.dp),
            contentPadding = PaddingValues(10.dp),
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colors.primary),
            horizontalArrangement = Arrangement.spacedBy(20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp),
            state = AppContext.categoriesListState
        ) {
            items(
                count = categories.size + 1,
                key = {
                    if (it == categories.size) {
                        -1
                    }
                    else {
                        categories[it].uId
                    }
                }) {
                if (it == categories.size) {
                    AddButton(isFabActive = false)
                }
                else {
                    CategoryFullCard(categories[it], colorConverter)
                }
            }
        }
    }
    else {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AddButton(isFabActive = false)
        }
    }
}