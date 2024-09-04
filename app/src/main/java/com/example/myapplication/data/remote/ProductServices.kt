package com.example.myapplication.data.remote

import com.example.myapplication.data.model.ProductDM
import retrofit2.http.GET

interface ProductServices {
    @GET("products")
    suspend fun getAllProducts(): ProductDM
}