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
    val categories: LiveData<List<Category>>
    private var categoriesRep: CategoriesRepository

    init {
        val dao = DbManager.getInstance(context).categories()
        categoriesRep = CategoriesRepository(dao)
        categories = categoriesRep.categories
    }

    fun add(category: Category) {
        viewModelScope.launch {
            categoriesRep.add(category)
        }
    }

    fun update(category: Category) {
        viewModelScope.launch {
            categoriesRep.update(category)
        }
    }

    fun remove(category: Category) {
        viewModelScope.launch {
            categoriesRep.remove(category)
        }
    }
}