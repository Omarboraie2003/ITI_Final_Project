package com.example.myapplication.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters

@Entity(tableName = "products")
data class Product(
    @PrimaryKey
    val id: Int,
    val availabilityStatus: String,
    val brand: String?="",
    val category: String,
    val description: String,
    val discountPercentage: Double,
    val price: Double,
    val rating: Double,
    val stock: Int,
    val title: String,
    var isFavorite: Boolean = false
) {

}