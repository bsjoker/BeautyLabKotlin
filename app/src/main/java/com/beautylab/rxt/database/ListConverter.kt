package com.beautylab.rxt.database

import androidx.room.TypeConverter
import com.beautylab.rxt.models.RecipesModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*
import kotlin.collections.ArrayList

class ListConverter {
    var gson = Gson()

    @TypeConverter
    fun fromTimestamp(data: String?): List<RecipesModel>? {

        if (data == null){
            return Collections.emptyList()
        }
        //val listType = object : TypeToken<ArrayList<String>>() {}.type
        //return gson.fromJson(data, listType)
        return gson.fromJson(data, Array<RecipesModel>::class.java).toList()

    }

    @TypeConverter
    fun someObjectListToString(someObjects: List<RecipesModel>?): String? {
        return gson.toJson(someObjects)
    }
}