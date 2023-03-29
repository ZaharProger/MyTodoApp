package com.example.mytodoapp.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
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
    contentViewModel: ContentViewModel = ContentViewModel(),
    categoryViewModel: CategoryViewModel = CategoryViewModel(LocalContext.current)) {

    val navController = rememberNavController()
    val navItems = listOf(
        NavBarItem(Routes.TASKS, R.string.tasks_caption, R.drawable.ic_tasks),
        NavBarItem(Routes.CATEGORIES, R.string.categories_caption, R.drawable.ic_categories)
    )

    val scaffoldState = rememberScaffoldState(
        snackbarHostState = SnackbarHostState()
    )

    val headerText by contentViewModel.topAppBarHeader.observeAsState("")
    val categories by categoryViewModel.categories.observeAsState(listOf())

    val isDialogOpen = remember {
        mutableStateOf(false)
    }
    val isSnackBarActive = remember {
        mutableStateOf(false)
    }

    val sheetRadius = 10.dp

    AppContext.apply {
        this.contentViewModel = contentViewModel
        this.categoryViewModel = categoryViewModel
        this.sheetState = rememberModalBottomSheetState(
            initialValue = ModalBottomSheetValue.Hidden,
            confirmValueChange = { it != ModalBottomSheetValue.HalfExpanded },
            skipHalfExpanded = true
        )
        this.categoriesListState = rememberLazyGridState()
        this.isDialogOpen = isDialogOpen
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
            if (isSnackBarActive.value) {
                val snackbarText = stringResource(id = R.string.deletion_complete)
                LaunchedEffect(
                    key1 = "",
                    block = {
                        val snackBarHandler = scaffoldState.snackbarHostState.showSnackbar(
                            message = snackbarText,
                            duration = SnackbarDuration.Short
                        )

                        when (snackBarHandler) {
                            SnackbarResult.Dismissed -> isSnackBarActive.value = false
                            else -> {}
                        }
                    }
                )
            }

            Column(
                modifier = Modifier
                    .padding(it)
            ) {
                PageView(navController, categories)
                if (isDialogOpen.value) {
                    val dialogData = when (AppContext.currentRoute) {
                        Routes.TASKS.stringValue ->
                            stringResource(id = R.string.delete_tasks_title) to
                                    stringResource(id = R.string.delete_tasks_caption)
                        else ->
                            stringResource(id = R.string.delete_categories_title) to
                                stringResource(id = R.string.delete_categories_caption)
                    }

                    AlertMessageDialog(dialogData, isDialogOpen, isSnackBarActive)
                }
            }
        }
    }
}