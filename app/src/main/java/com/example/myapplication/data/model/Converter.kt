package com.example.myapplication.data.model

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.ByteArrayOutputStream

public class Converter {
   @TypeConverter
   fun fromStringList(value: String?): List<String>? {
      val listType = object : TypeToken<List<String>>() {}.type
      return Gson().fromJson(value, listType)
   }

   @TypeConverter
   fun fromListString(list: List<String>?): String? {
      return Gson().toJson(list)
      }
}