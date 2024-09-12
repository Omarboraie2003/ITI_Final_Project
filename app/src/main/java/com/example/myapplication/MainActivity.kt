package com.example.myapplication

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.presentation.cart.FragmentCart
import com.example.myapplication.presentation.favourite.FragmentFavourite
import com.example.myapplication.presentation.home.HomeFragment
import com.example.myapplication.presentation.profile_feature.FragmentProfile
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
        val navHostFragment =
            supportFragmentManager.findFragmentById(binding.fragmentContainerView.id) as NavHostFragment
        navController =
            navHostFragment.navController// Set up the BottomNavigationView with NavControllerNavigationUI.setupWithNavController(binding.bottomNavigation, navController)
        Log.d("MainActivity", "NavController initialized: $navController")
        /* val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView.setupWithNavController(navController) */

        binding.bottomNavigationView.setupWithNavController(navController)

        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.homeIc -> {
                    /*supportFragmentManager.beginTransaction()
                        .replace(R.id.home_fragment,HomeFragment())
                        .addToBackStack("Home").setReorderingAllowed(true)
                        .commit()*/
                    true
                    navController.navigate(R.id.home_fragment)
                    supportActionBar?.title = "Home"
                    true
                }

                R.id.profileIc -> {
                    /*supportFragmentManager.beginTransaction()
                        .replace(R.id.profile_fragment,FragmentProfile())
                        .addToBackStack("Profile")
                        .setReorderingAllowed(true).commit()*/
                    navController.navigate(R.id.profile_fragment)
                    supportActionBar?.title = "Profile"

                    true
                }

                R.id.cartIc -> {
                    /*supportFragmentManager.beginTransaction()
                        .replace(R.id.cartFragment,FragmentCart())
                        .addToBackStack("Cart")
                        .setReorderingAllowed(true).commit()*/
                    navController.navigate(R.id.cart_fragment)
                    supportActionBar?.title = "Cart"
                    true
                }

                R.id.favIc -> {
                    /*supportFragmentManager.beginTransaction()
                        .replace(R.id.favourites_fragment,FragmentFavourite())
                        .addToBackStack("Profile")
                        .setReorderingAllowed(true).commit()*/
                    navController.navigate(R.id.favourites_fragment)
                    supportActionBar?.title = "Favorites"
                    true
                }

                else -> false
            }

        }



        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.home_fragment -> {
                    binding.bottomNavigationView.menu.findItem(R.id.homeIc).isChecked = true
                    supportActionBar?.title = "Home"
                }

                R.id.profile_fragment -> {
                    binding.bottomNavigationView.menu.findItem(R.id.profileIc).isChecked = true
                    supportActionBar?.title = "Profile"
                }

                R.id.cart_fragment -> {
                    binding.bottomNavigationView.menu.findItem(R.id.cartIc).isChecked = true
                    supportActionBar?.title = "Cart"
                }

                R.id.favourites_fragment -> {
                    binding.bottomNavigationView.menu.findItem(R.id.favIc).isChecked = true
                    supportActionBar?.title = "Favorites"
                }
            }


            /*supportFragmentManager.addOnBackStackChangedListener {
            val fragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView)
            when (fragment) {
                is HomeFragment -> BottomNavigationView.menu.findItem(R.id.homeIc).isChecked = true
                is FragmentFavourite -> BottomNavigationView.menu.findItem(R.id.favIc).isChecked = true
                is FragmentProfile -> BottomNavigationView.menu.findItem(R.id.profileIc).isChecked = true
                is FragmentCart -> BottomNavigationView.menu.findItem(R.id.cartIc).isChecked = true
            }
        }

         */


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


            onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (navController.currentDestination?.id == R.id.home_fragment) {
                        // If on the home fragment, exit the app
                        isEnabled = false
                        onBackPressedDispatcher.onBackPressed()
                    } else {
                        // Navigate up the back stack to the previous fragment
                        navController.navigateUp()
                    }
                }
            })


        }

    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}



