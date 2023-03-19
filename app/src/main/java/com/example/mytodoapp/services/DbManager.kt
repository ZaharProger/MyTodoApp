package com.example.mytodoapp.services

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.mytodoapp.entities.db.CategoriesDao
import com.example.mytodoapp.entities.db.Category

@Database(entities = [Category::class], version = 1)
abstract class DbManager: RoomDatabase() {
    abstract fun categories(): CategoriesDao

    companion object {
        private var dbInstance: DbManager? = null

        fun getInstance(context: Context): DbManager {
            return dbInstance ?: synchronized(this) {
                val newInstance = Room.databaseBuilder(
                    context,
                    DbManager::class.java,
                    "todo_db"
                ).build()

                dbInstance = newInstance
                newInstance
            }
        }
    }
}