package com.example.demo.controller

import com.example.demo.model.Todo
import com.example.demo.model.TodoRequestBody
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
class TodoController(private val todoService: TodoControllerInterface) {
    @GetMapping("/todos")
    @ResponseStatus(HttpStatus.OK)
    fun getTodos() : List<Todo> {
        return todoService.getTodos()
    }

    @PostMapping("/todos")
    @ResponseStatus(HttpStatus.CREATED)
    fun createTodos(@RequestBody requestBody: TodoRequestBody) : Todo {
        return todoService.createTodos(requestBody)
    }
}