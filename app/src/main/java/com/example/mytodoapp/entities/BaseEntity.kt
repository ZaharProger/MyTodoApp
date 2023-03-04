package com.example.mytodoapp.entities

open class BaseEntity(pId: ULong) {
    var id: ULong

    init {
        id = pId
    }
}