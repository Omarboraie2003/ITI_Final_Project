package com.example.myapplication.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDao {
    @Insert
    suspend fun insertUser(user: User)

    @Query("SELECT * FROM user_info WHERE email = :email AND password = :password")
    suspend fun login(email: String, password: String): User?

    @Query("SELECT EXISTS(SELECT 1 FROM user_info WHERE email = :email)")
    suspend fun doesEmailExist(email: String): Boolean

    @Query("SELECT EXISTS(SELECT 1 FROM user_info WHERE username = :userName)")
    suspend fun doesUserNameExist(userName: String): Boolean


    @Query(value = "SELECT * FROM user_info WHERE email = :email LIMIT 1")
    suspend fun getUserByEmail(email: String): User?

    @Query("DELETE FROM user_info WHERE username = :userName")
    suspend fun deleteUserByUserName(userName: String)

}