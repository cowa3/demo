package com.example.demo.repository

import com.example.demo.model.Todo
import com.example.demo.service.TodoServiceInterface
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Repository
import java.sql.ResultSet

@Repository
class TodoRepository(val jdbcTemplate: JdbcTemplate) : TodoServiceInterface {
    override fun getTodos(): List<Todo> {
        return jdbcTemplate.query(
                """select userId, id, title, completed from todo"""
        ) { rs: ResultSet, _:Int ->
            Todo(rs.getInt("userId"),
                    rs.getInt("id"),
                    rs.getString("title"),
                    rs.getBoolean("completed")
            )
        }
    }
}