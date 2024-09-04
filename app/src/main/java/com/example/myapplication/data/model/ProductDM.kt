package com.example.myapplication.data.model

import com.google.gson.annotations.SerializedName

data class ProductDM(
    @SerializedName("products")
    val products: List<Product>
)