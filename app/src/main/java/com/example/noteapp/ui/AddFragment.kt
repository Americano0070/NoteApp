package com.example.noteapp.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.noteapp.R
import com.example.noteapp.databinding.FragmentAddBinding

class AddFragment : Fragment(R.layout.fragment_add) {

    private val binding: FragmentAddBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding){


        }
    }

}