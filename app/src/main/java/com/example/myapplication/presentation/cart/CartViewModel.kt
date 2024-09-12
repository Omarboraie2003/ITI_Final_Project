package com.example.myapplication.presentation.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.model.Product
import com.example.myapplication.domain.repo.ProductsRepository
import kotlinx.coroutines.launch

class CartViewModel (private val repository: ProductsRepository) : ViewModel() {
    private val _inCartProducts = MutableLiveData<List<Product>>()
    val inCartProducts: LiveData<List<Product>> get() = _inCartProducts
    fun loadInCartProducts() {
        viewModelScope.launch {
            _inCartProducts.value = repository.getInCartProducts()
        }
    }

    fun removeProductFromInCart(product: Product) {
        viewModelScope.launch {
            repository.removeProductFromInCart(product)
            loadInCartProducts()
        }
    }

    fun clearCart() {
        viewModelScope.launch {
            repository.clearCart()// Assuming you have a method in your repository to clear the cart
        }
    }
}