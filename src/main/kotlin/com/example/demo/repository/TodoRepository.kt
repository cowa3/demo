package com.example.demo.repository

import com.example.demo.model.Todo
import com.example.demo.service.TodoServiceInterface
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository
import java.sql.ResultSet
import javax.print.attribute.standard.DateTimeAtCompleted


@Repository
class TodoRepository(val jdbcTemplate: JdbcTemplate) : TodoServiceInterface {
    override fun getTodos(): List<Todo> {
        return jdbcTemplate.query(
                """select userId, id, title, completed from todo order by id"""
        ) { rs: ResultSet, _: Int ->
            Todo(rs.getInt("userId"),
                    rs.getInt("id"),
                    rs.getString("title"),
                    rs.getBoolean("completed")
            )
        }
    }

    override fun createTodos(requestBody: TodoRequestBody): Todo {
        return jdbcTemplate.query(
                """insert into todo(userId, title) values ('${requestBody.userId}', '${requestBody.title}') returning userId, id, title, completed"""
        ) { rs: ResultSet, _: Int ->
            Todo(rs.getInt("userId"),
                    rs.getInt("id"),
                    rs.getString("title"),
                    rs.getBoolean("completed")
            )
        }[0]
    }
}