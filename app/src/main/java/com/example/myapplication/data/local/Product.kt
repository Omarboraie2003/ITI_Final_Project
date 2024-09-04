package com.example.myapplication.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "products")
data class Product (
    @PrimaryKey val id: Int,
    val title: String,
    @ColumnInfo("description")val desc: String,
    val category: String,
    val price: Double,
    val image:List<String> ,
    var isFavorite: Boolean = false,
    val brand: String,
    val rating: Double,
    val stock: Int,

)

