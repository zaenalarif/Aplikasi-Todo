package com.example.todoapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TodoViewModel: ViewModel() {
    private val _todos = MutableLiveData<ArrayList<Todo>>()

    val todos : LiveData<ArrayList<Todo>>
        get() = _todos

    init {
        _todos.value = arrayListOf(
            Todo(1, "Mandi")
        )
    }

    fun addTodo(text: String) {
        val newId = _todos.value!!.size + 1
        _todos.value!!.add(Todo(newId, text))
        _todos.setValue(_todos.value)
    }


    fun removeTodo(pos: Int) {
        _todos.value!!.removeAt(pos)
        _todos.setValue(_todos.value)
    }

    fun updateTodo(pos: Int, text: String) {
        _todos.value!![pos].task = text
        _todos.setValue(_todos.value)
    }
}
