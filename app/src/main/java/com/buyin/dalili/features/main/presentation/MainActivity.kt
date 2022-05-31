package com.buyin.dalili.features.main.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.buyin.dalili.R
import com.buyin.dalili.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var navHostFragment: NavHostFragment
    lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
            binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()

    }


    private fun init() {
        initNavigation()
        setToolbar()
        setupNavBottom()
    }

    private fun initNavigation() {
        navHostFragment =
            (supportFragmentManager.findFragmentById(R.id.fragment_navigation_container)
                    as NavHostFragment)

        navController = navHostFragment.findNavController()

        appBarConfiguration = AppBarConfiguration.Builder(navController.graph).build()

    }

    private fun setToolbar() {
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration)
    }


    private fun setupNavBottom() {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.item_login -> {
                    supportActionBar?.hide()
                }

                else -> {
                }
            }
        }

    }
}