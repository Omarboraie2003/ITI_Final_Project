package com.example.myapplication.domain.repo

import android.util.Log
import com.example.myapplication.data.local.ProductDao
import com.example.myapplication.data.model.Product
import com.example.myapplication.data.remote.ProductModule.apiService

class ProductsRepository(private val productDao:ProductDao) {
    suspend fun fetchAndSaveProducts() {
        try {
            // Fetch products from the remote API
            val products = apiService.getAllProducts()

            // Get the current favorite products from the database
            val favoriteProducts = productDao.getFavoriteProducts()
            val favoriteMap = favoriteProducts.associateBy { it.id }

            //Get the current inCart products from the database
            val inCartProducts =productDao.getInCartProducts()
            val incCartMap = inCartProducts.associateBy { it.id }

            // Merge favorite status with fetched products
            val mergedFavouriteProducts = products.products.map { product ->
                product.isFavorite = favoriteMap.containsKey(product.id)
                product
            }
            // Merge inCart status with fetched products
            val mergedINCartProducts=products.products.map { product ->
                product.inCart=incCartMap.containsKey(product.id)
                product
            }
            // Save the merged products to the database
            if (mergedINCartProducts.isNotEmpty()) {
                productDao.insertAllProducts(mergedINCartProducts)
            }

            // Save the merged products to the database
            if (mergedFavouriteProducts.isNotEmpty()) {
                productDao.insertAllProducts(mergedFavouriteProducts)
            }
        } catch (e: Exception) {
            Log.e("error", "fetchAndSaveProducts: $e", )
            // Handle exceptions (e.g., logging)
        }
    }

    suspend fun getAllProducts(): List<Product> {
        return productDao.getAllProducts()
    }

    suspend fun getFavoriteProducts(): List<Product> {
        return productDao.getFavoriteProducts()
    }
    suspend fun getInCartProducts(): List<Product> {
        return productDao.getInCartProducts()
    }

    suspend fun addProductToFavorites(product: Product) {
        product.isFavorite = true
        productDao.updateProduct(product)
    }
    suspend fun addProductToInCart(product: Product) {
        product.inCart = true
        productDao.updateProduct(product)
    }

    suspend fun removeProductFromInCart(product: Product) {
        product.inCart = false
        productDao.updateProduct(product)
    }
    suspend fun removeProductFromFavorites(product: Product) {
        product.isFavorite = false
        productDao.updateProduct(product)
    }

    suspend fun deleteProduct(product: Product) {
        productDao.deleteProduct(product)
    }

    suspend fun deleteProductById(id: Int) {
        productDao.deleteProductById(id)
    }

    suspend fun clearCart() {
        productDao.clearCart() // Assuming you have a method in your DAO to clear the cart
    }

}

