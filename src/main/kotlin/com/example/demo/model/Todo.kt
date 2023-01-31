package com.example.demo.model

data class Todo(
        val userId: Int,
        val id: Int,
        val title: String,
        val completed: Boolean
)


data class TodoRequestBody(
        val userId: Int,
        val title: String,
)