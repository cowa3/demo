package com.example.demo.controller

import com.example.demo.model.Todo
import com.example.demo.model.TodoRequestBody

interface TodoControllerInterface {
    fun getTodos(): List<Todo>

    fun createTodos(requestBody: TodoRequestBody): Todo
}