package com.example.demo.controller

import com.example.demo.model.Todo
import com.example.demo.model.TodoRequestBody
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.github.database.rider.core.api.dataset.DataSet
import com.github.database.rider.junit5.api.DBRider
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.*

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DBRider
class TodoApiControllerTest {
    @Autowired
    lateinit var restTemplate: TestRestTemplate

    @Test
    @DataSet(value = ["todo.yml"])
    fun タスク一覧を取得できる() {
        val header = HttpHeaders()
        header.contentType = MediaType.APPLICATION_JSON

        val response = restTemplate.exchange("/todos", HttpMethod.GET, HttpEntity(null, header), String::class.java)
        val mapper = jacksonObjectMapper()
        val articles: List<Todo> = mapper.readValue(response.body!!)

        Assertions.assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
        Assertions.assertThat(articles.size).isEqualTo(2)
        articles.forEachIndexed { index, todo ->
            val userId: Int = index + 1
            assertTodo(todo, userId)
        }
    }

    @Test
    @DataSet(value = ["todo.yml"])
    fun タスクを作成できる() {
        val header = HttpHeaders()
        header.contentType = MediaType.APPLICATION_JSON
        val userId = 3
        val requestBody = TodoRequestBody(userId, "task${userId}")

        val response = restTemplate.exchange("/todos", HttpMethod.POST, HttpEntity(requestBody, header), String::class.java)
        val mapper = jacksonObjectMapper()
        val todo: Todo = mapper.readValue(response.body!!)

        Assertions.assertThat(response.statusCode).isEqualTo(HttpStatus.CREATED)
        assertTodo(todo, 3)
    }

    fun assertTodo(todo: Todo, userId: Int) {
        Assertions.assertThat(todo.userId).isEqualTo(userId)
        Assertions.assertThat(todo.id).isInstanceOf(Integer::class.java)
        Assertions.assertThat(todo.title).isEqualTo("task${userId}")
        Assertions.assertThat(todo.completed).isEqualTo(false)
    }
}