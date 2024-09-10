package com.example.myapplication.presentation.favourite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.data.local.ProductDao
import com.example.myapplication.data.local.ProductDatabase
import com.example.myapplication.databinding.FragmentFavouritesFragmentBinding
import com.example.myapplication.domain.repo.ProductsRepository


class FragmentFavourite : Fragment() {
    private var _binding: FragmentFavouritesFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: FavouriteViewModel
    private lateinit var repository: ProductsRepository
    private lateinit var productDao: ProductDao
    private lateinit var productAdapter: FavouriteAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavouritesFragmentBinding.inflate(inflater, container, false)
        val root: View = binding.root
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
       productDao = ProductDatabase.getDatabase(requireContext()).productDao()
        repository = ProductsRepository(productDao)
        val viewModelFactory = FavouriteViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory)[FavouriteViewModel::class.java]


        productAdapter = FavouriteAdapter(mutableListOf()) { product ->
            if (product.isFavorite) {
                viewModel.removeProductFromFavorites(product)
            }
        }
        binding.recyclerView.adapter = productAdapter

        viewModel.favouriteProducts.observe(viewLifecycleOwner) { products ->
            if (products.isEmpty()) {
                binding.recyclerView.visibility = View.GONE
                binding.noItem.visibility = View.VISIBLE
            } else {
                binding.recyclerView.visibility = View.VISIBLE
                binding.noItem.visibility = View.GONE
                productAdapter.updateProducts(products)
            }
        }

//        viewModel.favouriteProducts.observe(viewLifecycleOwner) { products ->
//            productAdapter = FavouriteAdapter(products) { product ->
//                if (product.isFavorite) {
//                    viewModel.removeProductFromFavorites(product)
//                }
//            }
//          binding.recyclerView.adapter = productAdapter
//        }
        viewModel.loadFavouriteProducts()

    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}