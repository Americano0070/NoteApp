package com.example.noteapp.ui

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.noteapp.R
import com.example.noteapp.dataBase.Priority
import com.example.noteapp.dataBase.ToDoData
import com.example.noteapp.databinding.FragmentUpdateBinding
import com.example.noteapp.viewModel.SharedViewModel
import com.example.noteapp.viewModel.ToDoViewModel


class UpdateFragment : Fragment() {

    private lateinit var binding: FragmentUpdateBinding
    private val args by navArgs<UpdateFragmentArgs>()
    private val mSharedViewModel: SharedViewModel by viewModels()
    private val mToDoViewModel: ToDoViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUpdateBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)

        binding.editTitle.setText(args.transferItem.tittle)
        binding.editDesc.setText(args.transferItem.description)
        binding.editSpinner.setSelection(mSharedViewModel.priorityColor(args.transferItem.priority))
        binding.editSpinner.onItemSelectedListener = mSharedViewModel.listener

        return binding.root
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_edit, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.edit_save -> updateData()
            R.id.edit_delete -> removeItem()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun updateData() {
        val title = binding.editTitle.text.toString()
        val description = binding.editDesc.text.toString()
        val getPriority = binding.editSpinner.selectedItem.toString()

        val validation = mSharedViewModel.verifyDataFromUser(title, description)
        if (validation) {
            val updateData = ToDoData(
                args.transferItem.id,
                title,
                mSharedViewModel.parsePriority(getPriority),
                description
            )

            mToDoViewModel.updateData(updateData)
            Toast.makeText(requireContext(), "Successfully update", Toast.LENGTH_SHORT).show()

            findNavController().navigate(R.id.action_updateFragment_to_homeFragment)
        } else {
            Toast.makeText(requireContext(), "Check the data for errors!", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun removeItem() {

        val builder = AlertDialog.Builder(requireContext())

        builder.setPositiveButton("Yes") { _, _ ->
            mToDoViewModel.deleteItem(args.transferItem)
            Toast.makeText(
                requireContext(),
                "Successfully Removed: ${args.transferItem.tittle}",
                Toast.LENGTH_SHORT
            ).show()
            findNavController().navigate(R.id.action_updateFragment_to_homeFragment)
        }
        builder.setNegativeButton("No") { _, _ -> }
        builder.setTitle("Delete `${args.transferItem.tittle}`?")
        builder.setMessage("Are you sure you want to remove `${args.transferItem.tittle}`?")
        builder.create().show()
    }

}