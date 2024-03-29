package com.example.mytodoapp.viewmodels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mytodoapp.entities.db.Category
import com.example.mytodoapp.entities.db.repositories.CategoriesRepository
import com.example.mytodoapp.services.DbManager
import kotlinx.coroutines.launch

class CategoryViewModel(context: Context): ViewModel() {
    private var _categories: LiveData<List<Category>>
    val categories: LiveData<List<Category>>
        get() = _categories
    private var categoriesRep: CategoriesRepository

    init {
        val dao = DbManager.getInstance(context).categories()
        categoriesRep = CategoriesRepository(dao)
        _categories = categoriesRep.categories
    }

    fun add(category: Category) {
        viewModelScope.launch {
            categoriesRep.add(category)
        }
    }

    fun remove(category: Category) {
        viewModelScope.launch {
            categoriesRep.remove(category)
        }
    }
}