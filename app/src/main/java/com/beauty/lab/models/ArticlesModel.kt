package com.beauty.lab.models

import androidx.room.*
import com.beauty.lab.database.ListConverter

@Entity(tableName = "articlesData")
data class ArticlesModel(
    @PrimaryKey(autoGenerate = true) var id: Long? = 0,
    @ColumnInfo(name = "name") val name: String = "",
    @ColumnInfo(name = "title") val title: String = "",
    @TypeConverters(ListConverter::class)
    val articles: List<RecipesModel>
)