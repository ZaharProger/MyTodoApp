package com.example.mytodoapp.entities.db.repositories

import androidx.lifecycle.LiveData
import com.example.mytodoapp.entities.db.Language
import com.example.mytodoapp.entities.db.dao.LanguagesDao

class LanguagesRepository(private val languagesDao: LanguagesDao) {
    val languages: LiveData<List<Language>> = languagesDao.getLanguages()

    suspend fun add(language: Language) {
        languagesDao.addLanguage(language)
    }
}