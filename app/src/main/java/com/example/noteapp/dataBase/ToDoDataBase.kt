package com.example.noteapp.dataBase

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todo_table")
data class ToDoDataBase(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var tittle: String,
    var priority: Priority,
    var description: String
)