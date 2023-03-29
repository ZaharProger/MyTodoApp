package com.example.mytodoapp.entities.db.repositories

import androidx.lifecycle.LiveData
import com.example.mytodoapp.entities.db.CategoriesDao
import com.example.mytodoapp.entities.db.Category

class CategoriesRepository(private val categoriesDao: CategoriesDao) {
    val categories: LiveData<List<Category>> = categoriesDao.getCategories()

    suspend fun add(category: Category) {
        categoriesDao.addCategory(category)
    }

    suspend fun remove(category: Category) {
        categoriesDao.removeCategory(category)
    }
}