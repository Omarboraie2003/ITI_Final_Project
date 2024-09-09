package com.example.myapplication.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_info")
data class User(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val fullname: String,
    val username: String,
    val email: String,
    val phoneNumber: String,
    val address :String,
    val gender :String,
    val password: String
)
