package com.ahmed.areebmovies.data.roommodels

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@ProvidedTypeConverter
class GenresConverter {
    @TypeConverter
    fun fromArrayList(list: ArrayList<Int>?): String? {
        return Gson().toJson(list)
    }

    @TypeConverter
    fun toArrayList(json: String?): ArrayList<Int>? {
        return Gson().fromJson(json, object : TypeToken<ArrayList<Int>?>() {}.type)
    }
}