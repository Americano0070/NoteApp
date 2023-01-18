package com.example.noteapp.dataBase.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController

import androidx.recyclerview.widget.RecyclerView
import com.example.noteapp.R
import com.example.noteapp.dataBase.ToDoData
import com.example.noteapp.dataBase.model.Priority
import com.example.noteapp.databinding.RowLayoutBinding
import com.example.noteapp.ui.HomeFragmentDirections



class ListAdapter : RecyclerView.Adapter<ListAdapter.ViewHolder>() {

    var list = emptyList<ToDoData>()

    class ViewHolder(private val binding: RowLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onBind(toDoData: ToDoData) {
            binding.textTitle.text = toDoData.tittle
            binding.textDescription.text = toDoData.description

            when (toDoData.priority) {
                Priority.HIGH -> binding.itemIndicator.setCardBackgroundColor(
                    ContextCompat.getColor(
                        binding.itemIndicator.context,
                        R.color.red
                    )
                )

                Priority.MEDIUM -> binding.itemIndicator.setCardBackgroundColor(
                    ContextCompat.getColor(
                        binding.itemIndicator.context,
                        R.color.yellow
                    )
                )

                Priority.LOW -> binding.itemIndicator.setCardBackgroundColor(
                    ContextCompat.getColor(
                        binding.itemIndicator.context,
                        R.color.green
                    )
                )

            }

            val action = HomeFragmentDirections.actionHomeFragmentToUpdateFragment(toDoData)
            binding.layoutBackground.setOnClickListener { view ->
                view.findNavController().navigate(action)
            }


        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = RowLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(list[position])
    }

    override fun getItemCount(): Int = list.size

    fun setData(toDoData: List<ToDoData>) {
        this.list = toDoData
        notifyDataSetChanged()
    }

}

