package com.beauty.lab.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.beauty.lab.models.ArticlesModel


@Dao
interface ArticlesDao {
    @Query("SELECT * FROM articlesData")
    fun getAll(): LiveData<List<ArticlesModel>>

    @Query("SELECT * FROM articlesData WHERE name == :name")
    fun getByName(name: String): ArticlesModel

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(articlesData: ArticlesModel)

    @Update
    fun update(articlesData: ArticlesModel)

    @Delete
    fun delete(articlesData: ArticlesModel)
}