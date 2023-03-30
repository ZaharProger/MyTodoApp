package com.example.mytodoapp.entities.db.repositories

import androidx.lifecycle.LiveData
import com.example.mytodoapp.entities.db.Task
import com.example.mytodoapp.entities.db.dao.TasksDao

class TasksRepository(private val tasksDao: TasksDao) {
    val tasks: LiveData<List<Task>> = tasksDao.getTasks()

    suspend fun add(task: Task) {
        tasksDao.addTask(task)
    }

    suspend fun update(task: Task) {
        tasksDao.updateTask(task)
    }

    suspend fun remove(task: Task) {
        tasksDao.removeTask(task)
    }
}