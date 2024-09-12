package com.example.myapplication.presentation.checkout

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.myapplication.databinding.FragmentCheckoutBinding
import androidx.navigation.fragment.navArgs
import com.example.myapplication.R
import com.example.myapplication.data.local.ProductDao
import com.example.myapplication.data.local.ProductDatabase
import com.example.myapplication.domain.repo.ProductsRepository
import com.example.myapplication.presentation.cart.CartViewModel
import com.example.myapplication.presentation.cart.CartViewModelFactory

class CheckoutFragment : Fragment() {

    private var _binding: FragmentCheckoutBinding? = null
    private val binding get() = _binding!!

    // Using Safe Args to receive the data
    private val args: CheckoutFragment by navArgs()
    private lateinit var viewModel: CartViewModel
    private lateinit var repository: ProductsRepository
    private lateinit var productDao: ProductDao

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCheckoutBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navController = findNavController()
        val args = CheckoutFragmentArgs.fromBundle(requireArguments())
        productDao = ProductDatabase.getDatabase(requireContext()).productDao()
        repository = ProductsRepository(productDao)
        val viewModelFactory = CartViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory)[CartViewModel::class.java]

        // Retrieve data passed via Safe Args
        val productNames = args.productNames
        val productPrices = args.productPrices

        // Check if the cart is empty
        if (productNames.isEmpty() || productPrices.isEmpty()) {
            // Show an empty cart message
            binding.productNameTv.text = "Your cart is empty."
            binding.totalPriceTv.text = ""
            binding.confirmPurchaseBtn.isEnabled = false // Disable the confirm button
        } else {
            // Display the product names
            binding.productNameTv.text = productNames.joinToString("\n\n")

            // Calculate the total price
            val totalPrice = productPrices.sum()

            // Format the total price to two decimal places
            val formattedPrice = String.format("%.2f", totalPrice)

            // Update UI with the formatted total price
            binding.totalPriceTv.text = "Total: $$formattedPrice"

            // Handle confirm purchase button click
            binding.confirmPurchaseBtn.setOnClickListener {
                showConfirmationDialog(navController)
                viewModel.clearCart()
            }
        }
    }

    private fun showConfirmationDialog(navController: NavController) {
        AlertDialog.Builder(requireContext())
            .setTitle("Confirm Purchase")
            .setMessage("Are you sure you want to confirm the purchase? Payment will be done on delivery.")
            .setPositiveButton("Yes") { _, _ ->
                val navOptions = androidx.navigation.NavOptions.Builder()
                    .setPopUpTo(navController.graph.startDestinationId, true) // This clears the back stack
                    .build()
                findNavController().navigate(R.id.afterCheckOutFragment, null, navOptions)

                // Increment completed purchases in SharedPreferences
                val sharedPreferences = requireContext().getSharedPreferences("userData", Context.MODE_PRIVATE)
                val editor = sharedPreferences.edit()
                val completedPurchases = sharedPreferences.getInt("completePurchases", 0)
                editor.putInt("completePurchases", completedPurchases + 1)
                editor.apply()
            }
            .setNegativeButton("No", null)
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

