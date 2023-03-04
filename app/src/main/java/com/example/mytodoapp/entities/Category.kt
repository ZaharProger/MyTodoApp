package com.example.mytodoapp.entities

import androidx.compose.ui.graphics.Color

class Category(pId: ULong, pName: String, pColor: Color) : BaseEntity(pId) {
    var name: String
    var color: Color

    init {
        name = pName
        color = pColor
    }
}