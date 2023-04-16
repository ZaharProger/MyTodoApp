package com.example.mytodoapp.entities.http

import com.example.mytodoapp.entities.db.Language

data class PostResponseData(
    val translatedText: String,
    val detectedSourceLanguage: Language
)
