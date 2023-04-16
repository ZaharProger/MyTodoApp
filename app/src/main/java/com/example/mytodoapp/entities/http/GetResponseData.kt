package com.example.mytodoapp.entities.http

import com.example.mytodoapp.entities.db.Language

data class GetResponseData(
    val languages: List<Language>
)