package com.example.demo.service

import com.example.demo.controller.TodoControllerInterface
import com.example.demo.model.Todo
import org.springframework.stereotype.Service

@Service
class TodoService(private val todoRepository: TodoServiceInterface): TodoControllerInterface {
    override fun getTodos(): List<Todo> {
        return todoRepository.getTodos()
    }

    override fun createTodos(requestBody: TodoRequestBody): Todo {
        return todoRepository.createTodos(requestBody);
    }
}