package com.example.mytodoapp.entities

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import com.example.mytodoapp.constants.Routes
import com.example.mytodoapp.entities.db.BaseEntity
import com.example.mytodoapp.viewmodels.CategoryViewModel
import com.example.mytodoapp.viewmodels.ContentViewModel

object AppContext {
    var contentViewModel: ContentViewModel? = null
    var categoryViewModel: CategoryViewModel? = null
    @OptIn(ExperimentalMaterialApi::class)
    lateinit var sheetState: ModalBottomSheetState
    lateinit var categoriesListState: LazyGridState
    lateinit var tasksListState: LazyListState
    var currentRoute: String = Routes.TASKS.stringValue
    private var _selectedItems = mutableListOf<BaseEntity>()
    var selectedItems: MutableList<BaseEntity>
        get() = _selectedItems
        set(value) {
            _selectedItems.clear()
            _selectedItems.addAll(value)
        }
}
