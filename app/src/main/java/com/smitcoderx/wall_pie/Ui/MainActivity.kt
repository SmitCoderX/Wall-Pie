package com.smitcoderx.wall_pie.Ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.smitcoderx.wall_pie.R
import com.smitcoderx.wall_pie.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment
        val navController = navHostFragment.navController
        binding.apply {
            bottomNav.setupWithNavController(navController)
            bottomNav.background = null
            bottomNav.menu.getItem(2).isEnabled = false
            fab.setOnClickListener {
                navController.navigate(R.id.searchFragment)
            }
        }

        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.singleFragment) {
                binding.apply {
                    bottomNav.visibility = View.GONE
                    bottomAppBar.visibility = View.GONE
                    fab.visibility = View.GONE
                }
            } else {
                binding.apply {
                    bottomNav.visibility = View.VISIBLE
                    bottomAppBar.visibility = View.VISIBLE
                    fab.visibility = View.VISIBLE
                }
            }
        }
    }
}