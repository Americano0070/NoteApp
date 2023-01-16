package com.example.noteapp.ui

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.view.animation.OvershootInterpolator
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.noteapp.R
import com.example.noteapp.dataBase.adapter.ListAdapter
import com.example.noteapp.dataBase.ToDoData
import com.example.noteapp.databinding.FragmentHomeBinding
import com.example.noteapp.repository.SwipeToDelete
import com.example.noteapp.viewModel.SharedViewModel
import com.example.noteapp.viewModel.ToDoViewModel
import com.google.android.material.snackbar.Snackbar
import jp.wasabeef.recyclerview.animators.FadeInAnimator
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator

class HomeFragment : Fragment() {

    private val adapter: ListAdapter by lazy { ListAdapter() }
    private lateinit var binding: FragmentHomeBinding
    private val mToDoViewModel: ToDoViewModel by viewModels()
    private val mSharedViewModel: SharedViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)

        setUpRecyclerView()

        mToDoViewModel.getAllData.observe(viewLifecycleOwner, Observer { data ->
            mSharedViewModel.checkIfDatabaseEmpty(data)
            adapter.setData(data)
        })

        mSharedViewModel.emptyDataBase.observe(viewLifecycleOwner, Observer {
            showEmptyDataBaseView(it)
        })

        binding.nextButton.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_addFragment)

        }

        return binding.root
    }


    private fun setUpRecyclerView() {
        val recyclerView = binding.recyclerView

        recyclerView.itemAnimator = FadeInAnimator().apply {
            addDuration = 500
        }
        recyclerView.adapter = adapter

        // Swipe Detele
        swipeToDelete(recyclerView)
    }

    // Swipe Detele
    private fun swipeToDelete(recyclerView: RecyclerView) {
        val swipeToDeleteCallback = object : SwipeToDelete() {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val itemToDelete = adapter.list[viewHolder.adapterPosition]
                mToDoViewModel.deleteItem(itemToDelete)
                adapter.notifyItemRemoved(viewHolder.adapterPosition)
                restoreDeleteUndo(viewHolder.itemView, itemToDelete, viewHolder.adapterPosition)
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeToDeleteCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    private fun restoreDeleteUndo(view: View, deleteItem: ToDoData, position: Int) {

        val snackbar = Snackbar.make(
            view, "Delete '${deleteItem.tittle}'",
            Snackbar.LENGTH_LONG
        )
        snackbar.setAction("undo") {
            mToDoViewModel.insertData(deleteItem)
            adapter.notifyItemChanged(position)
        }
        snackbar.show()

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.home_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.home_delete_all -> deleteAllData()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteAllData() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") { _, _ ->
            mToDoViewModel.deleteAll()
            Toast.makeText(
                requireContext(), "Successfully Everything!", Toast.LENGTH_SHORT
            ).show()
        }
        builder.setNegativeButton("No") { _, _ -> }
        builder.setTitle("Delete Everything?")
        builder.setMessage("Are you sure you want to remove everything?")
        builder.create().show()
    }

    private fun showEmptyDataBaseView(emptyDataBase: Boolean) {
        if (emptyDataBase) {
            binding.noData.visibility = View.VISIBLE
            binding.dataTitle.visibility = View.VISIBLE
        } else {
            binding.noData.visibility = View.INVISIBLE
            binding.dataTitle.visibility = View.INVISIBLE
        }
    }

}
