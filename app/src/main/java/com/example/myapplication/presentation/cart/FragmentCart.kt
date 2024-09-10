package com.example.myapplication.presentation.cart

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.R
import com.example.myapplication.data.local.ProductDao
import com.example.myapplication.data.local.ProductDatabase
import com.example.myapplication.data.model.Product
import com.example.myapplication.databinding.FragmentCartFragmentBinding
import com.example.myapplication.databinding.FragmentFavouritesFragmentBinding
import com.example.myapplication.domain.repo.ProductsRepository
import com.example.myapplication.presentation.favourite.FavouriteAdapter
import com.example.myapplication.presentation.favourite.FavouriteViewModel
import com.example.myapplication.presentation.favourite.FavouriteViewModelFactory


class FragmentCart : Fragment() {
    private var _binding: FragmentCartFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: CartViewModel
    private lateinit var repository: ProductsRepository
    private lateinit var productDao: ProductDao
    private lateinit var productAdapter: CartAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCartFragmentBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerViewCart.layoutManager = LinearLayoutManager(requireContext())
        productDao = ProductDatabase.getDatabase(requireContext()).productDao()
        repository = ProductsRepository(productDao)
        val viewModelFactory =CartViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory)[CartViewModel::class.java]


        productAdapter = CartAdapter(mutableListOf()) { product ->
            if (product.inCart) {
                showDeleteConfirmationDialog(product)
               // viewModel.removeProductFromInCart(product)
            }
        }
        binding.recyclerViewCart.adapter = productAdapter

        viewModel.inCartProducts.observe(viewLifecycleOwner) { products ->
            if (products.isEmpty()) {
                binding.recyclerViewCart.visibility = View.GONE
                binding.noItem.visibility = View.VISIBLE
            } else {
                binding.recyclerViewCart.visibility = View.VISIBLE
                binding.noItem.visibility = View.GONE
                productAdapter.updateProducts(products)
            }
        }


        viewModel.loadInCartProducts()
    }


    private fun showDeleteConfirmationDialog(product: Product) {
        AlertDialog.Builder(requireContext())
            .setTitle("Delete Item")
            .setMessage("Are you sure you want to delete this product from the list?")
            .setPositiveButton("Yes") { dialog, _ ->
                // If "Yes" is clicked, remove the product from the list
                viewModel.removeProductFromInCart(product)
                dialog.dismiss()
            }
            .setNegativeButton("No") { dialog, _ ->
                // If "No" is clicked, just dismiss the dialog
                dialog.dismiss()
            }
            .create().show()
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}