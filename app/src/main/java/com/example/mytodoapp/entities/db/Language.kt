package com.example.mytodoapp.entities.db

import androidx.room.ColumnInfo
import androidx.room.Entity

@kotlinx.serialization.Serializable
@Entity(tableName = "languages")
data class Language(
    @ColumnInfo(name = "code")
    var code: String,
    @ColumnInfo(name = "name")
    var name: String
): BaseEntity()
