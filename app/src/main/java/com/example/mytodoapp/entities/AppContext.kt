package com.example.mytodoapp.entities

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetState
import com.example.mytodoapp.entities.db.Category
import com.example.mytodoapp.viewmodels.ContentViewModel

object AppContext {
    var contentViewModel: ContentViewModel? = null
    var categories: List<Category> = listOf()
    @OptIn(ExperimentalMaterialApi::class)
    lateinit var sheetState: ModalBottomSheetState
}
