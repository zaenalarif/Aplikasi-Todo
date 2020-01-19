package com.example.todoapp

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.todoapp.database.Todo
import com.example.todoapp.database.TodoDAO
import com.example.todoapp.database.TodoDatabase
import com.example.todoapp.database.TodoRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class TodoViewModel(application: Application): AndroidViewModel(application) {

    private val repository : TodoRepository
    private val todoDAO: TodoDAO

    private var _todos : LiveData<List<Todo>>


    val todos : LiveData<List<Todo>>
        get() = _todos


    private var vmJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.IO + vmJob)

    init {
        todoDAO = TodoDatabase.getInstance(application).todoDAO()
        repository = TodoRepository(todoDAO)
        _todos = repository.allTodos
    }

    fun addTodo(text: String) {
        uiScope.launch {
            repository.insert(Todo(0, text))
        }
    }


    fun removeTodo(todo: Todo) {
        uiScope.launch {
            repository.delete(todo)
        }
    }

    fun updateTodo(pos: Int, text: String) {

    }

    override fun onCleared() {
        super.onCleared()
        vmJob.cancel()
    }
}
