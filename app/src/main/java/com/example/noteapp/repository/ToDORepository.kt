package com.example.noteapp.repository

import androidx.lifecycle.LiveData
import androidx.room.Query
import com.example.noteapp.dataBase.ToDoDao
import com.example.noteapp.dataBase.ToDoData

class ToDORepository(private val toDoDao: ToDoDao) {

    val getAllData: LiveData<List<ToDoData>> = toDoDao.getAllData()
    val sortByHighPriority: LiveData<List<ToDoData>> = toDoDao.sortByHighPriority()
    val sortByLowPriority: LiveData<List<ToDoData>> = toDoDao.sortByLowPriority()

    suspend fun insertData(toDoData: ToDoData) {
        toDoDao.insert(toDoData)
    }

    suspend fun updateData(toDoData: ToDoData) {
        toDoDao.updateData(toDoData)
    }

    suspend fun deleteItem(toDoData: ToDoData) {
        toDoDao.deleteItem(toDoData)
    }

    suspend fun deleteAll() {
        toDoDao.deleteAll()
    }

    fun searchDataBase(searchQuery: String): LiveData<List<ToDoData>> {
       return toDoDao.searchDataBase(searchQuery)
    }

}