package com.example.noteapp.ui

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.noteapp.R
import com.example.noteapp.adapter.ListAdapter
import com.example.noteapp.databinding.FragmentHomeBinding
import com.example.noteapp.viewModel.ToDoViewModel

class HomeFragment : Fragment(R.layout.fragment_home) {

    private val binding: FragmentHomeBinding by viewBinding()
    private val mToDoViewModel: ToDoViewModel by viewModels()
    private val adapter: ListAdapter by lazy { ListAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            setHasOptionsMenu(true)

            binding.recyclerView.adapter = adapter
            mToDoViewModel.getAllData.observe(viewLifecycleOwner, Observer { data ->
                adapter.setData(data)
            })
            nextButton.setOnClickListener {
                findNavController().navigate(R.id.action_homeFragment_to_addFragment)

            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.home_menu, menu)
    }

}