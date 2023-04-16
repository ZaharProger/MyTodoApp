package com.example.mytodoapp.services

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.mytodoapp.entities.db.dao.CategoriesDao
import com.example.mytodoapp.entities.db.Category
import com.example.mytodoapp.entities.db.Language
import com.example.mytodoapp.entities.db.Task
import com.example.mytodoapp.entities.db.dao.LanguagesDao
import com.example.mytodoapp.entities.db.dao.TasksDao

@Database(
    entities = [Category::class, Task::class, Language::class],
    version = 10,
    exportSchema = true
)
abstract class DbManager: RoomDatabase() {
    abstract fun categories(): CategoriesDao
    abstract fun tasks(): TasksDao
    abstract fun languages(): LanguagesDao

    companion object {
        private var dbInstance: DbManager? = null

        fun getInstance(context: Context): DbManager {
            return dbInstance ?: synchronized(this) {
                val newInstance = Room.databaseBuilder(
                    context,
                    DbManager::class.java,
                    "todo_db"
                )
                .fallbackToDestructiveMigration()
                .build()

                dbInstance = newInstance
                newInstance
            }
        }
    }
}