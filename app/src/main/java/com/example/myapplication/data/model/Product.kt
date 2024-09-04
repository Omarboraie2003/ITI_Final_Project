package com.example.myapplication.data.model

data class Product(
    val availabilityStatus: String,
    val brand: String,
    val category: String,
    val description: String,
    val discountPercentage: Double,
    val id: Int,
    val images: List<String>,
    val price: Double,
    val rating: Double,
    val reviews: List<Review>,
    val stock: Int,
    val title: String,
    var isFavorite: Boolean = false // Add a field to mark as favorite
) {

}