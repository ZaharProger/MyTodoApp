package com.example.mytodoapp.entities.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.mytodoapp.entities.db.Language

@Dao
interface LanguagesDao {
    @Query("SELECT * FROM languages")
    fun getLanguages() : LiveData<List<Language>>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun addLanguage(language: Language)
}