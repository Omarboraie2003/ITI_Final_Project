package com.example.myapplication.presentation.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.myapplication.R
import com.example.myapplication.data.local.ProductDao
import com.example.myapplication.data.local.ProductDatabase
import com.example.myapplication.data.model.Product
import com.example.myapplication.databinding.FragmentHomeFragmentBinding

import com.example.myapplication.domain.repo.ProductsRepository



class HomeFragment : Fragment() {
    private var _binding: FragmentHomeFragmentBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel:HomeViewModel
    private lateinit var repository: ProductsRepository
    private lateinit var productDao: ProductDao
    private lateinit var productAdapter: HomeAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeFragmentBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerview.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        productDao = ProductDatabase.getDatabase(requireContext()).productDao()
       repository = ProductsRepository(productDao)
        val viewModelFactory =HomeViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory)[HomeViewModel::class.java]
        viewModel.products.observe(viewLifecycleOwner) { products ->
            productAdapter = HomeAdapter(products,
                onFavoriteClick = { product ->
                    if (product.isFavorite) {
                        viewModel.removeProductFromFavorites(product)
                    } else {
                        viewModel.addProductToFavorites(product)
                    }
                },
                onInCartClick = { product ->
                    if (product.inCart) {
                        viewModel.removeProductFromCart(product)
                    } else {
                        viewModel.addProductToCart(product)
                    }
                }
            )
            binding.recyclerview.adapter = productAdapter
        }
        viewModel.fetchProducts()
    }



    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }




}