package com.example.mytodoapp.components.content

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.mytodoapp.R
import com.example.mytodoapp.viewmodels.ContentViewModel

@Composable
fun CategoriesPage(contentViewModel: ContentViewModel) {
    contentViewModel.setTopAppBarHeader(
        stringResource(
            id = R.string.categories_caption
        )
    )

    Text(
        text = "Categories will be here",
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(MaterialTheme.colors.primary),
        color = MaterialTheme.colors.secondary,
        style = MaterialTheme.typography.body2
    )
}