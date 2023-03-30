package com.example.mytodoapp.viewmodels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mytodoapp.entities.db.Task
import com.example.mytodoapp.entities.db.repositories.TasksRepository
import com.example.mytodoapp.services.DbManager
import kotlinx.coroutines.launch

class TaskViewModel(context: Context): ViewModel() {
    private var _tasks: LiveData<List<Task>>
    val tasks: LiveData<List<Task>>
        get() = _tasks
    private var tasksRep: TasksRepository

    init {
        val dao = DbManager.getInstance(context).tasks()
        tasksRep = TasksRepository(dao)
        _tasks = tasksRep.tasks
    }

    fun add(task: Task) {
        viewModelScope.launch {
            tasksRep.add(task)
        }
    }

    fun update(task: Task) {
        viewModelScope.launch {
            tasksRep.update(task)
        }
    }

    fun remove(task: Task) {
        viewModelScope.launch {
            tasksRep.remove(task)
        }
    }
}