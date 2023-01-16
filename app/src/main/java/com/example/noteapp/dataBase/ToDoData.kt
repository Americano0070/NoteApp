package com.example.noteapp.dataBase

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.noteapp.dataBase.model.Priority
import kotlinx.parcelize.Parcelize

@Entity(tableName = "todo_table")
@Parcelize
data class ToDoData(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var tittle: String,
    var priority: Priority,
    var description: String
) : Parcelable