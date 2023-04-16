package com.example.mytodoapp.viewmodels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mytodoapp.constants.PrefsKeys
import com.example.mytodoapp.constants.RequestKeys
import com.example.mytodoapp.entities.db.Language
import com.example.mytodoapp.entities.db.repositories.LanguagesRepository
import com.example.mytodoapp.services.ConnectionChecker
import com.example.mytodoapp.services.DbManager
import com.example.mytodoapp.services.WebClient
import kotlinx.coroutines.*

class LanguageViewModel(context: Context): ViewModel() {
    private var _languages: LiveData<List<Language>>
    val languages: LiveData<List<Language>>
        get() = _languages
    private var languagesRep: LanguagesRepository
    private val webClient = WebClient.getInstance("https://text-translator2.p.rapidapi.com")

    init {
        val dao = DbManager.getInstance(context).languages()
        languagesRep = LanguagesRepository(dao)
        _languages = languagesRep.languages

        val prefs = context.getSharedPreferences(
            PrefsKeys.PREFS_NAME.stringValue,
            Context.MODE_PRIVATE
        )
        val isCached = prefs.getBoolean(PrefsKeys.IS_CACHED.stringValue, false)

        if (_languages.value == null && ConnectionChecker.checkConnection(context) && !isCached) {
            viewModelScope.launch {
                val task = async(Dispatchers.IO) {
                    val response = withContext(Dispatchers.IO) {
                        webClient.getLanguages()
                    }
                    if (response.code() == 200) response.body() else null
                }

                val prefsEditor = prefs.edit()
                prefsEditor.putBoolean(PrefsKeys.IS_CACHED.stringValue, true)
                prefsEditor.apply()

                val responseData = task.await()

                responseData?.let {
                    val languagesFromResponse = it.data.languages.distinctBy { language ->
                        language.name
                    }
                    languagesFromResponse.forEach { language ->
                        languagesRep.add(language)
                    }
                }
            }
        }
    }

    suspend fun translate(data: Map<String, String>): String {
        val task = viewModelScope.async(Dispatchers.IO) {
            val response = withContext(Dispatchers.IO) {
                webClient.translate(data)
            }

            if (response.code() == 200) response.body() else null
        }

        val responseData = task.await()
        return responseData?.data?.translatedText ?: data[RequestKeys.TEXT.stringValue]!!
    }
}