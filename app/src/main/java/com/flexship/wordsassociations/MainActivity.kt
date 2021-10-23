package com.flexship.wordsassociations

import android.R
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.flexship.wordsassociations.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }

    override fun onResume() {
        super.onResume()
        setupActionBarWithNavController(findNavController(binding.navHostFragment.id))
    }

    override fun onSupportNavigateUp(): Boolean {
        return (Navigation.findNavController(binding.navHostFragment).navigateUp()
                || super.onSupportNavigateUp())
    }
}