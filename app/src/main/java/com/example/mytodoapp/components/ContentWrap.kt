package com.example.mytodoapp.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.example.mytodoapp.AppSnackBar
import com.example.mytodoapp.R
import com.example.mytodoapp.components.content.AddButton
import com.example.mytodoapp.components.content.AlertMessageDialog
import com.example.mytodoapp.components.content.PageHeader
import com.example.mytodoapp.components.content.category.AddCategory
import com.example.mytodoapp.components.navbar.NavBar
import com.example.mytodoapp.components.navbar.PageView
import com.example.mytodoapp.constants.Routes
import com.example.mytodoapp.entities.AppContext
import com.example.mytodoapp.entities.ui.NavBarItem
import com.example.mytodoapp.viewmodels.CategoryViewModel
import com.example.mytodoapp.viewmodels.ContentViewModel
import com.example.mytodoapp.viewmodels.TaskViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ContentWrap(
    contentViewModel: ContentViewModel = ContentViewModel(),
    categoryViewModel: CategoryViewModel = CategoryViewModel(LocalContext.current),
    taskViewModel: TaskViewModel = TaskViewModel(LocalContext.current)
) {
    val navController = rememberNavController()
    val navItems = listOf(
        NavBarItem(Routes.TASKS, R.string.tasks_caption, R.drawable.ic_tasks),
        NavBarItem(Routes.CATEGORIES, R.string.categories_caption, R.drawable.ic_categories)
    )

    val scaffoldState = rememberScaffoldState(
        snackbarHostState = SnackbarHostState()
    )

    val currentCategory = remember {
        mutableStateOf(-1L)
    }

    val headerText by contentViewModel.topAppBarHeader.observeAsState("")
    val isFabActive by contentViewModel.isFabActive.observeAsState(false)
    val isDialogOpen by contentViewModel.isDialogOpen.observeAsState(false)
    val isDeleteActive by contentViewModel.isDeleteActive.observeAsState(false)
    val categories by categoryViewModel.categories.observeAsState(listOf())
    val tasks by taskViewModel.tasks.observeAsState(listOf())

    val sheetRadius = 10.dp

    AppContext.apply {
        this.contentViewModel = contentViewModel
        this.categoryViewModel = categoryViewModel
        this.taskViewModel = taskViewModel
        this.sheetState = rememberModalBottomSheetState(
            initialValue = ModalBottomSheetValue.Hidden,
            confirmValueChange = { it != ModalBottomSheetValue.HalfExpanded },
            skipHalfExpanded = true
        )
        this.categoriesListState = rememberLazyGridState()
        this.tasksListState = rememberLazyListState()
    }

    ModalBottomSheetLayout(
        modifier = Modifier
            .fillMaxWidth()
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
                    AddCategory(sheetRadius, categories.size)
            }
        },
        scrimColor = com.example.mytodoapp.ui.theme.Scrim
    ) {
        Scaffold(
            scaffoldState = scaffoldState,
            floatingActionButton = {
                if (isFabActive && categories.isNotEmpty()) {
                    AddButton(
                        null,
                        isDeleteActive = isDeleteActive,
                        hasCaption = false
                    )
                }
            },
            floatingActionButtonPosition = FabPosition.End,
            isFloatingActionButtonDocked = false,
            snackbarHost = {
                SnackbarHost(
                    hostState = scaffoldState.snackbarHostState
                ) { snackbarData: SnackbarData ->
                    AppSnackBar(snackbarData)
                }
            },
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
                PageView(navController, categories, tasks, isDeleteActive, currentCategory)
                if (isDialogOpen) {
                    val dialogData = when (AppContext.currentRoute) {
                        Routes.TASKS.stringValue ->
                            stringResource(id = R.string.delete_tasks_title) to
                                    stringResource(id = R.string.delete_tasks_caption)
                        else ->
                            stringResource(id = R.string.delete_categories_title) to
                                stringResource(id = R.string.delete_categories_caption)
                    }

                    AlertMessageDialog(dialogData, isDeleteActive)
                }
            }
        }
    }
}