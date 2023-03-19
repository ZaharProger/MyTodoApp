package com.example.mytodoapp.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.example.mytodoapp.R
import com.example.mytodoapp.components.content.PageHeader
import com.example.mytodoapp.components.content.category.AddCategory
import com.example.mytodoapp.components.navbar.NavBar
import com.example.mytodoapp.components.navbar.PageView
import com.example.mytodoapp.constants.Routes
import com.example.mytodoapp.entities.AppContext
import com.example.mytodoapp.entities.ui.NavBarItem
import com.example.mytodoapp.viewmodels.CategoryViewModel
import com.example.mytodoapp.viewmodels.ContentViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ContentWrap(
    contentViewModel: ContentViewModel,
    categoryViewModel: CategoryViewModel = CategoryViewModel(LocalContext.current)) {

    val navController = rememberNavController()
    val navItems = listOf(
        NavBarItem(Routes.TASKS, R.string.tasks_caption, R.drawable.ic_tasks),
        NavBarItem(Routes.CATEGORIES, R.string.categories_caption, R.drawable.ic_categories),
        NavBarItem(Routes.TRASH, R.string.trash_caption, R.drawable.ic_trash)
    )

    val headerText by contentViewModel.topAppBarHeader.observeAsState("")
    val categories by categoryViewModel.categories
        .observeAsState(categoryViewModel.categories.value ?: listOf())

    AppContext.contentViewModel = contentViewModel
    AppContext.categories = categories
    AppContext.sheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        skipHalfExpanded = false
    )

    val isFullScreen by remember {
        mutableStateOf(false)
    }

    val sheetRadius = if (isFullScreen) 0.dp else 10.dp
    val sheetModifier = if (isFullScreen) {
        Modifier.fillMaxSize()
    }
    else {
        Modifier.fillMaxWidth()
    }

    ModalBottomSheetLayout(
        modifier = sheetModifier
            .background(MaterialTheme.colors.primary),
        sheetState = AppContext.sheetState,
        sheetShape = RoundedCornerShape(
            topStart = sheetRadius,
            topEnd = sheetRadius
        ),
        sheetContent = {
            when (headerText) {
                stringResource(id = R.string.tasks_caption) -> {}
                stringResource(id = R.string.categories_caption) ->
                    AddCategory(categoryViewModel, sheetRadius)
                stringResource(id = R.string.trash_caption) -> {}
            }
        },
        scrimColor = com.example.mytodoapp.ui.theme.Scrim
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    backgroundColor = MaterialTheme.colors.primary,
                    modifier = Modifier
                        .fillMaxWidth(),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    PageHeader(headerText)
                }
            },
            bottomBar = {
                BottomAppBar(
                    backgroundColor = MaterialTheme.colors.primary,
                    modifier = Modifier
                        .fillMaxWidth(),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    NavBar(navController, navItems)
                }
            }
        ) {
            Column(
                modifier = Modifier
                    .padding(it)
            ) {
                PageView(navController)
            }
        }
    }
}