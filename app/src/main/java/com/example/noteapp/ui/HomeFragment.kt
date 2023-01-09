package com.example.noteapp.ui

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.noteapp.R
import com.example.noteapp.adapter.ListAdapter
import com.example.noteapp.databinding.FragmentHomeBinding
import com.example.noteapp.viewModel.SharedViewModel
import com.example.noteapp.viewModel.ToDoViewModel

class HomeFragment : Fragment(R.layout.fragment_home) {

    private val binding: FragmentHomeBinding by viewBinding()
    private val mToDoViewModel: ToDoViewModel by viewModels()
    private val mSharedViewModel: SharedViewModel by viewModels()
    private val adapter: ListAdapter by lazy { ListAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            setHasOptionsMenu(true)

            binding.recyclerView.adapter = adapter
            mToDoViewModel.getAllData.observe(viewLifecycleOwner, Observer { data ->
                mSharedViewModel.checkIfDatabaseEmpty(data)
                adapter.setData(data)
            })

            mSharedViewModel.emptyDataBase.observe(viewLifecycleOwner, Observer {
                showEmptyDataBaseViews(it)
            })

            nextButton.setOnClickListener {
                findNavController().navigate(R.id.action_homeFragment_to_addFragment)

            }
        }
    }

    private fun showEmptyDataBaseViews(emptyDataBase: Boolean) {
        if (emptyDataBase) {
            binding.noData.visibility = View.VISIBLE
            binding.dataTitle.visibility = View.VISIBLE
        } else {
            binding.noData.visibility = View.INVISIBLE
            binding.dataTitle.visibility = View.INVISIBLE
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.home_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.home_delete_all -> DeleteAllData()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun DeleteAllData() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") { _, _ ->
            mToDoViewModel.deleteAll()
            Toast.makeText(requireContext(), "Successfully Removed Everything!", Toast.LENGTH_SHORT)
                .show()
        }
        builder.setNegativeButton("No") { _, _ -> }
        builder.setTitle("Delete Everything!")
        builder.setMessage("Are you sure you want to remove everything?")
        builder.create().show()
    }


}