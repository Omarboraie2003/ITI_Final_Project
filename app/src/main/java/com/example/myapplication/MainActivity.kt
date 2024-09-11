package com.example.myapplication

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.myapplication.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setSupportActionBar(binding.toolbar)
        setContentView(binding.root)
        supportActionBar?.title = "Home"
        val navHostFragment =supportFragmentManager.findFragmentById(binding.fragmentContainerView.id) as NavHostFragment
        navController = navHostFragment.navController// Set up the BottomNavigationView with NavControllerNavigationUI.setupWithNavController(binding.bottomNavigation, navController)
        Log.d("MainActivity", "NavController initialized: $navController")
        /* val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView.setupWithNavController(navController) */


        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.homeIc -> {
                    navController.navigate(R.id.home_fragment)
                    supportActionBar?.title = "Home"
                    true
                }

                R.id.profileIc -> {
                    navController.navigate(R.id.profile_fragment)
                    supportActionBar?.title = "My Profile"
                    true
                }

                R.id.cartIc -> {
                    navController.navigate(R.id.cart_fragment)
                    supportActionBar?.title = "My Cart"
                    true
                }

                R.id.favIc -> {
                    navController.navigate(R.id.favourites_fragment)
                    supportActionBar?.title = "Favorites"
                    true
                }

                else -> false
            }

        }



        /*navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.home_fragment -> bottomNavigationView.menu.findItem(R.id.home_fragment).isChecked =
                    true

                R.id.profile_fragment -> bottomNavigationView.menu.findItem(R.id.profile_fragment).isChecked =
                    true

                R.id.cartFragment -> bottomNavigationView.menu.findItem(R.id.cartFragment).isChecked =
                    true

                R.id.favourites_fragment -> bottomNavigationView.menu.findItem(R.id.favourites_fragment).isChecked =
                    true
            }

         */

            val hideBottomNavFraments =
                listOf(R.id.accountOptionsFragment, R.id.loginFragment, R.id.registerFragment)


            navController.addOnDestinationChangedListener { _, destination, _ ->
                if (destination.id in hideBottomNavFraments) {
                    binding.bottomNavigationView.visibility = BottomNavigationView.GONE
                    supportActionBar?.hide()
                } else {
                    binding.bottomNavigationView.visibility = BottomNavigationView.VISIBLE
                    supportActionBar?.show()
                }
            }
        }
    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

}


