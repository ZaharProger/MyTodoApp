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
import com.example.mytodoapp.R
import com.example.mytodoapp.components.content.PageHeader
import com.example.mytodoapp.components.content.category.AddCategory
import com.example.mytodoapp.components.navbar.NavBar
import com.example.mytodoapp.components.navbar.PageView
import com.example.mytodoapp.constants.Routes
import com.example.mytodoapp.entities.AppContext
import com.example.mytodoapp.entities.ui.NavBarItem
import com.example.mytodoapp.ui.theme.Shapes
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
        NavBarItem(Routes.CATEGORIES, R.string.categories_caption, R.drawable.ic_categories),
        NavBarItem(Routes.TRASH, R.string.trash_caption, R.drawable.ic_trash)
    )

    val scaffoldState = rememberScaffoldState()

    val headerText by contentViewModel.topAppBarHeader.observeAsState("")
    val categories by categoryViewModel.categories.observeAsState(listOf())

    val isDialogOpen = remember {
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
        this.scaffoldState = scaffoldState
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
                stringResource(id = R.string.trash_caption) -> {}
            }
        },
        scrimColor = com.example.mytodoapp.ui.theme.Scrim
    ) {
        Scaffold(
            scaffoldState = scaffoldState,
            snackbarHost = {
                SnackbarHost(it) {
                    Snackbar(
                        backgroundColor = MaterialTheme.colors.primaryVariant,
                        contentColor = MaterialTheme.colors.secondary,
                        shape = Shapes.medium,
                    ) {}
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
                PageView(navController, categories)
                if (isDialogOpen.value) {
                    AlertMessageDialog(
                        title = stringResource(id = R.string.trash_move_title),
                        caption = stringResource(id = R.string.trash_move_caption),
                        isDialogOpen
                    )
                }
            }
        }
    }
}