package com.example.noteapp.ui

import android.os.Bundle
import android.text.TextUtils
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.noteapp.R
import com.example.noteapp.dataBase.Priority
import com.example.noteapp.dataBase.ToDoData
import com.example.noteapp.databinding.FragmentAddBinding
import com.example.noteapp.viewModel.ToDoViewModel

class AddFragment : Fragment(R.layout.fragment_add) {

    private val binding: FragmentAddBinding by viewBinding()
    private val mToDoViewModel: ToDoViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_add, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_save) {
            insertDataToDb()
        }
        return super.
        onOptionsItemSelected(item)
    }

    private fun insertDataToDb() {
        val mTitle = binding.titleName.toString()
        val mPriority = binding.spinner.selectedItem.toString()
        val mDescription = binding.description.toString()

        val validation = verifyDataFromUser(mTitle, mDescription)

        if (validation) {
            //insert data to Database

            val newData = ToDoData(
                0,
                mTitle,
                parsePriority(mPriority),
                mDescription
            )
            mToDoViewModel.insertData(newData)
            Toast.makeText(requireContext(), "Successfully new data", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_addFragment_to_homeFragment)
        } else {
            Toast.makeText(requireContext(), "Please fill out all fields", Toast.LENGTH_SHORT)
                .show()
        }
    }

    // TextViewni tekshirish
    private fun verifyDataFromUser(title: String, description: String): Boolean {
        return if (TextUtils.isEmpty(title) || TextUtils.isEmpty(description)) {
            false
        } else !(title.isEmpty() || description.isEmpty())
    }

    // Parse Priority
    private fun parsePriority(priority: String): Priority {
        return when (priority) {
            "High Priority" -> {
                Priority.HIGH
            }
            "Medium Priority" -> {
                Priority.MEDIUM
            }
            "Low Priority" -> {
                Priority.LOW
            }
            else -> Priority.LOW
        }
    }

}