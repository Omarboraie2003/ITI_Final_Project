package com.example.myapplication.presentation.favourite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.local.Product
import com.example.myapplication.domain.repo.ProductsRepository
import kotlinx.coroutines.launch

class FavouriteViewModel(private val repository: ProductsRepository) : ViewModel() {
private val _favouriteProducts= MutableLiveData<List<Product>>()
    val favouriteProducts:LiveData<List<Product>> get()=_favouriteProducts
    fun loadFavouriteProducts(){
        viewModelScope.launch {
            _favouriteProducts.value=repository.getFavoriteProducts()
        }
    }
    fun removeProductFromFavorites(product: Product) {
        viewModelScope.launch {
            repository.removeProductFromFavorites(product)
            loadFavouriteProducts()
        }
    }
}