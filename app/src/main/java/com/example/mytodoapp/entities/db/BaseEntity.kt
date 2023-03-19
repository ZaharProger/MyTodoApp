package com.example.mytodoapp.entities.db

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey

open class BaseEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "uid")
    var uId: Long = 0)