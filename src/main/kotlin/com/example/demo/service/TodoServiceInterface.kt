package com.example.demo.service

import com.example.demo.model.Todo

interface TodoServiceInterface {
    fun getTodos(): List<Todo>
}