package com.example.mytodoapp.entities.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface CategoriesDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun addCategory(category: Category)

    @Query("SELECT * FROM categories")
    fun getCategories() : LiveData<List<Category>>

    @Delete
    suspend fun removeCategory(category: Category): Int
}