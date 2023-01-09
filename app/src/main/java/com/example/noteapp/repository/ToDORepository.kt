package com.example.noteapp.repository

import androidx.lifecycle.LiveData
import com.example.noteapp.dataBase.ToDoDao
import com.example.noteapp.dataBase.ToDoData

class ToDORepository(private val toDoDao: ToDoDao) {

    val getAllData: LiveData<List<ToDoData>> = toDoDao.getAllData()

    suspend fun insertData(toDoData: ToDoData) {
        toDoDao.insert(toDoData)
    }


}