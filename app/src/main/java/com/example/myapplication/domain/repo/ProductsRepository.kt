/*package com.example.myapplication.domain.repo

import com.example.myapplication.data.local.ProductDao
import com.example.myapplication.data.local.Product
import com.example.myapplication.data.remote.ProductModule.apiService

class ProductsRepository(private val productDao:ProductDao) {
          suspend fun fetchAndSaveProducts() {
               try {
                    // Fetch products from the remote API
                    val products = apiService.getAllProducts()

                    // Get the current favorite products from the database
                    val favoriteProducts = productDao.getFavoriteProducts()
                    val favoriteMap = favoriteProducts.associateBy { it.id }

                    // Merge favorite status with fetched products
                    val mergedProducts = products.products.map { product ->
                         product.isFavorite = favoriteMap.containsKey(product.id)
                         product
                    }

                    // Save the merged products to the database
                    if (mergedProducts.isNotEmpty()) {
                         productDao.insertAllProducts(mergedProducts)
                    }
               } catch (e: Exception) {
                   val offlineData=productDao.getFavoriteProducts()
               }
          }

          suspend fun getAllProducts(): List<Product> {
               return productDao.getAllProducts()
          }

          suspend fun getFavoriteProducts(): List<Product> {
               return productDao.getFavoriteProducts()
          }

          suspend fun addProductToFavorites(product: Product) {
               product.isFavorite = true
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
     }


 */