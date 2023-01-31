package com.example.demo.service

import com.example.demo.model.Todo
import com.example.demo.model.TodoRequestBody

interface TodoServiceInterface {
    fun getTodos(): List<Todo>

    fun createTodos(requestBody: TodoRequestBody): Todo
}