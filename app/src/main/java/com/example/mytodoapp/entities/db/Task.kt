package com.example.mytodoapp.entities.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey

@kotlinx.serialization.Serializable
@Entity(
    tableName = "tasks",
    foreignKeys = [
        ForeignKey(
            entity = Category::class,
            childColumns = ["category_uid"],
            parentColumns = ["uid"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Language::class,
            childColumns = ["language_uid"],
            parentColumns = ["uid"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ]
)
data class Task(
    @ColumnInfo(name = "title")
    var title: String,
    @ColumnInfo(name = "data")
    var data: String,
    @ColumnInfo(name = "category_uid", index = true)
    var category: Long,
    @ColumnInfo(name = "language_uid", index = true)
    var language: Long? = null,
    @ColumnInfo(name = "notification_datetime")
    var notificationDateTime: Long? = null
): BaseEntity()