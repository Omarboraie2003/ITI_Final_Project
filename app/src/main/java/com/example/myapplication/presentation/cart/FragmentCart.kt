package com.example.myapplication.presentation.cart

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
        viewModel.inCartProducts.observe(viewLifecycleOwner) { products ->
            productAdapter = CartAdapter(products) { product ->
                if (product.inCart) {
                    viewModel.removeProductFromInCart(product)
                }
            }
            binding.recyclerViewCart.adapter = productAdapter
        }
        viewModel.loadInCartProducts()
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}