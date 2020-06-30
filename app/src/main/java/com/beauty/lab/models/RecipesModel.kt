package com.beauty.lab.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.beauty.lab.database.ListConverter

@Entity(tableName = "recipesData")
data class RecipesModel(
    @PrimaryKey(autoGenerate = true) var id: Long?,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "components") val components: Map<String, String>?,
    @TypeConverters(ListConverter::class)
    @ColumnInfo(name = "steps") val steps: List<String>,
    @ColumnInfo(name = "instructions") val instructions: String,
    @ColumnInfo(name = "rate") val rate: Int//,
    //val reviews: List<ReviewModel>
) {
    constructor() : this(
        null, "", "", null, emptyList(), "", 0
    )
}