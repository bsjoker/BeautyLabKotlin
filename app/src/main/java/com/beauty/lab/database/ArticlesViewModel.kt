package com.beauty.lab.database

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.beauty.lab.models.ArticlesModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ArticlesViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: ArticlesRepository
    val allArticles: LiveData<List<ArticlesModel>>

    init {
        val articlesDao = AppDatabase.getAppDataBase(
            application,
            viewModelScope
        ).articlesDao()
        repository = ArticlesRepository(articlesDao)
        allArticles = repository.allArticles
    }

    fun insert(articlesModel: ArticlesModel) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(articlesModel)
    }
}