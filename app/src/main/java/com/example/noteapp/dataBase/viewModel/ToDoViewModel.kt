package com.example.noteapp.dataBase.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.noteapp.dataBase.ToDoData
import com.example.noteapp.dataBase.ToDoDataBase
import com.example.noteapp.repository.ToDORepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ToDoViewModel(application: Application) : AndroidViewModel(application) {

    private val toDoDao = ToDoDataBase.getDataBase(application).toDoDao()
    private val repository: ToDORepository = ToDORepository(toDoDao)

    val getAllData: LiveData<List<ToDoData>> = repository.getAllData
    val sortByHighPriority: LiveData<List<ToDoData>> = repository.sortByHighPriority
    val sortByLowPriority: LiveData<List<ToDoData>> = repository.sortByLowPriority

    fun insertData(toDoData: ToDoData) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertData(toDoData)
        }
    }

    fun updateData(toDoData: ToDoData) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateData(toDoData)
        }
    }

    fun deleteItem(toDoData: ToDoData) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteItem(toDoData)
        }
    }

    fun deleteAll() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAll()
        }
    }

    fun searchDataBase(searchQuery: String): LiveData<List<ToDoData>> {
        return repository.searchDataBase(searchQuery)
    }
}