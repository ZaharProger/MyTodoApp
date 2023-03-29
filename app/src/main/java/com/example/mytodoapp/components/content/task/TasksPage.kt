package com.example.mytodoapp.components.content.task

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.mytodoapp.R
import com.example.mytodoapp.components.content.category.CategoryShortCard
import com.example.mytodoapp.entities.AppContext
import com.example.mytodoapp.entities.db.Category

@Composable
fun TasksPage(
    categories: List<Category>
) {
    AppContext.contentViewModel?.setTopAppBarHeader(
        stringResource(
            id = R.string.tasks_caption
        )
    )

    val actualCategoriesState = rememberLazyListState()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.primary)
    ) {
        LazyRow(
            state = actualCategoriesState,
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Transparent),
            contentPadding = PaddingValues(0.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            items(categories) {
                CategoryShortCard(it)
            }
        }

        LazyColumn(
            state = AppContext.tasksListState,
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colors.primary),
            contentPadding = PaddingValues(10.dp, 0.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

        }
    }
}