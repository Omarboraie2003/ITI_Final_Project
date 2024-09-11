package com.example.myapplication.presentation.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
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
        setupRecyclerView()
        setupViewModel()
        observeProducts()
        observeLoadingState()

        viewModel.fetchProducts()
    }
    private fun setupRecyclerView() {
        binding.recyclerview.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
    }


    private fun setupViewModel() {
        productDao = ProductDatabase.getDatabase(requireContext()).productDao()
        repository = ProductsRepository(productDao)
        val viewModelFactory = HomeViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory)[HomeViewModel::class.java]
    }


    private fun observeProducts() {
        viewModel.products.observe(viewLifecycleOwner) { products ->
            productAdapter = HomeAdapter(
                products,
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
                },
                onProductClick = { product ->
                    navigateToProductDetails(product)
                }
            )
            binding.recyclerview.adapter = productAdapter
        }
    }


    private fun observeLoadingState() {
        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) {
                binding.loading.visibility = View.VISIBLE
                binding.loading.playAnimation()
            } else {
                binding.loading.visibility = View.GONE
                binding.loading.pauseAnimation()
            }
        }
    }

    private fun navigateToProductDetails(product: Product) {
        val bundle = Bundle().apply {
            putString("productName", product.title)
            putString("productDescription", product.description)
            putDouble("productPrice", product.price)
            putString("productCategory", product.category)
            putString("productBrand", product.brand)
            putString("productImage", product.thumbnail)
            putDouble("productDisc",product.discountPercentage)
            putDouble("productRating", product.rating)
        }
        findNavController().navigate(R.id.action_home_fragment_to_productDetailsFragment, bundle)
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity).supportActionBar?.title = "Home" // Set your fragment's title here
    }




}