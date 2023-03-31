package com.example.mytodoapp.components.content.task

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.mytodoapp.R
import com.example.mytodoapp.components.content.category.CategoryShortCard
import com.example.mytodoapp.entities.AppContext
import com.example.mytodoapp.entities.db.Category
import com.example.mytodoapp.entities.db.Task
import com.example.mytodoapp.services.ColorConverter

@Composable
fun TasksPage(
    tasks: List<Task>,
    categories: List<Category>
) {
    AppContext.contentViewModel?.setTopAppBarHeader(
        stringResource(
            id = R.string.tasks_caption
        )
    )

    val actualCategoriesState = rememberLazyListState()

    val colorConverter = ColorConverter(16)

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
                CategoryShortCard(it, colorConverter)
            }
        }

        if (tasks.isNotEmpty()) {
            LazyColumn(
                state = AppContext.tasksListState,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colors.primary),
                contentPadding = PaddingValues(10.dp, 0.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(tasks) {
                    TaskShortCard(it, colorConverter, categories, LocalContext.current)
                }
            }
        }
        else {
            Text(
                text = stringResource(
                    id = R.string.not_found
                ),
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colors.primary)
                    .padding(10.dp),
                color = MaterialTheme.colors.secondary,
                style = MaterialTheme.typography.body2,
                textAlign = TextAlign.Center
            )
        }
    }
}