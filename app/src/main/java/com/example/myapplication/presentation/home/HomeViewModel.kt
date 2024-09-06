package com.example.myapplication.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.model.Product
import com.example.myapplication.domain.repo.ProductsRepository
import kotlinx.coroutines.launch

class HomeViewModel (private val repository: ProductsRepository) : ViewModel() {

    private val _products = MutableLiveData<List<Product>>()
    val products: LiveData<List<Product>> get() = _products


    fun fetchProducts() {
        viewModelScope.launch {
            repository.fetchAndSaveProducts()
            _products.value = repository.getAllProducts()
        }
    }

    fun addProductToFavorites(product: Product) {
        viewModelScope.launch {
            repository.addProductToFavorites(product)
        }
    }

    fun removeProductFromFavorites(product: Product) {
        viewModelScope.launch {
            repository.removeProductFromFavorites(product)
        }
    }
}