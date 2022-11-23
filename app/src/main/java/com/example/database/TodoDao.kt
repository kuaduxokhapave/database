package com.example.database

import androidx.room.*


@Dao
interface TodoDao {
    @get:Query("SELECT * FROM tasks")
    val all: List<TodoEntity>

    @Query("SELECT * FROM tasks WHERE id = :id")
    fun getById(id: Long): TodoEntity

    @Insert
    fun insert(todo: TodoEntity): Long

    @Update
    fun update(todo: TodoEntity)

    @Delete
    fun delete(todo: TodoEntity)
}