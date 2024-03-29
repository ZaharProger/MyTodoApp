package com.example.mytodoapp.components.content.category

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.mytodoapp.R
import com.example.mytodoapp.components.content.AddButton
import com.example.mytodoapp.entities.AppContext
import com.example.mytodoapp.entities.db.Category
import com.example.mytodoapp.services.ColorConverter

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CategoriesPage(
    navController: NavHostController,
    categories: List<Category>,
    currentCategory: MutableState<Long>
) {
    AppContext.contentViewModel?.setTopAppBarHeader(
        stringResource(
            id = R.string.categories_caption
        )
    )

    if (categories.isNotEmpty()) {
        val reversedCategories = categories.reversed()
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
                count = reversedCategories.size + 1,
                key = {
                    if (it == reversedCategories.size) {
                        -1
                    }
                    else {
                        reversedCategories[it].uId
                    }
                }) {
                if (it == reversedCategories.size) {
                    AddButton(
                        Modifier.animateItemPlacement(),
                        isFabActive = false
                    )
                }
                else {
                    CategoryFullCard(
                        Modifier.animateItemPlacement(),
                        navController,
                        reversedCategories[it],
                        colorConverter,
                        currentCategory
                    )
                }
            }
        }
    }
    else {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AddButton(
                null,
                isFabActive = false,
            )
        }
    }
}