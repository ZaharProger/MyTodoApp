package com.example.mytodoapp.entities.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.mytodoapp.entities.db.Task

@Dao
interface TasksDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun addTask(task: Task)

    @Query("SELECT * FROM tasks")
    fun getTasks(): LiveData<List<Task>>

    @Update
    suspend fun updateTask(task: Task): Int

    @Delete
    suspend fun removeTask(task: Task): Int
}