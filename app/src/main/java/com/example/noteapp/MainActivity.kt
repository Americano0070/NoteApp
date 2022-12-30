package com.example.noteapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupActionBarWithNavController(findNavController(R.id.nav_host))
    }

    override fun onSupportNavigateUp(): Boolean {
        val navControler = findNavController(R.id.nav_host)
        return navControler.navigateUp() || super.onSupportNavigateUp()
    }
}