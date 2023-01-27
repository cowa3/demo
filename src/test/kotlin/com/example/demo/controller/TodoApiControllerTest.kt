package com.example.demo.controller

import com.example.demo.model.Todo
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

class TodoApiControllerTest {

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
            assertResult (response, articles, 2)

        }

        fun assertResult (response: ResponseEntity<String>, articles: List<Todo>, size: Int) {

            Assertions.assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
            Assertions.assertThat(articles.size).isEqualTo(size)

            articles.forEachIndexed {index, todo ->
                Assertions.assertThat(todo.userId).isEqualTo(index + 1)
                Assertions.assertThat(todo.id).isEqualTo(index + 1)
                Assertions.assertThat(todo.title).isEqualTo("task" + (index + 1))
                Assertions.assertThat(todo.completed).isEqualTo(false)
            }

        }
    }
}