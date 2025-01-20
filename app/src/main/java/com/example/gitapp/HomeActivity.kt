package com.example.gitapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.example.gitapp.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set up the toolbar
        val toolbar: Toolbar = binding.toolbar
        setSupportActionBar(toolbar)

        // Remove the default title to set custom titles for each fragment
        supportActionBar?.setDisplayShowTitleEnabled(false)

        // Bottom navigation setup
        binding.bottomNavigation.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_repositories -> loadFragment(RepositoryListFragment(), "GitHub Repository")
                R.id.nav_settings -> loadFragment(SettingsFragment(), "Settings")
            }
            true
        }

        // Load default fragment
        if (savedInstanceState == null) {
            binding.bottomNavigation.selectedItemId = R.id.nav_repositories
        }
    }

    private fun loadFragment(fragment: Fragment, title: String) {
        val username = intent.getStringExtra("USERNAME") ?: ""
        val bundle = Bundle().apply {
            putString("USERNAME", username)
        }
        fragment.arguments = bundle

        // Update the toolbar title dynamically
        binding.toolbarTitle.text = title

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentcontainer, fragment)
            .commit()
    }
}