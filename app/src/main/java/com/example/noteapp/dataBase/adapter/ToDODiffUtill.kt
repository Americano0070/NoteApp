package com.example.noteapp.dataBase.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.noteapp.dataBase.ToDoData


class ToDODiffUtill(
    private val oldList: List<ToDoData>,
    private val newList: List<ToDoData>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] === newList[newItemPosition]
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
                && oldList[oldItemPosition].tittle == newList[newItemPosition].tittle
                && oldList[oldItemPosition].priority == newList[newItemPosition].priority
                && oldList[oldItemPosition].description == newList[newItemPosition].description
    }
}