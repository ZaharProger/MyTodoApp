package com.example.mytodoapp.entities.db

import androidx.room.ColumnInfo
import androidx.room.Entity

@kotlinx.serialization.Serializable
@Entity(tableName = "categories")
data class Category(
    @ColumnInfo(name = "name")
    var name: String = "",
    @ColumnInfo(name = "color")
    var color: String = "00FFFFFF"): BaseEntity()