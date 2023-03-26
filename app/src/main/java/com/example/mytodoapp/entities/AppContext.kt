package com.example.mytodoapp.entities

import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.MutableState
import com.example.mytodoapp.viewmodels.CategoryViewModel
import com.example.mytodoapp.viewmodels.ContentViewModel

object AppContext {
    var contentViewModel: ContentViewModel? = null
    var categoryViewModel: CategoryViewModel? = null
    @OptIn(ExperimentalMaterialApi::class)
    lateinit var sheetState: ModalBottomSheetState
    lateinit var categoriesListState: LazyGridState
    lateinit var scaffoldState: ScaffoldState
    lateinit var isDialogOpen: MutableState<Boolean>
}
