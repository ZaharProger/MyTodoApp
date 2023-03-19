package com.example.mytodoapp.components.content.category

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.mytodoapp.R
import com.example.mytodoapp.components.AddButton
import com.example.mytodoapp.entities.AppContext

@Composable
fun CategoriesPage() {

    AppContext.contentViewModel?.setTopAppBarHeader(
        stringResource(
            id = R.string.categories_caption
        )
    )

    if (AppContext.categories.isNotEmpty()) {
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 128.dp),
            contentPadding = PaddingValues(0.dp),
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colors.primary)
                .padding(10.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            var counter = 0
            items(AppContext.categories) {
                if (counter == AppContext.categories.size - 1) {
                    AddButton(hasCaption = false)
                }
                else {
                    CategoryCard(it)
                }

                ++counter
            }
        }
    }
    else {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AddButton()
        }
    }
}