package com.example.demo.controller

import com.example.demo.model.Todo

interface TodoControllerInterface {
    fun getTodos(): List<Todo>
}