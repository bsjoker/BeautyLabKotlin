package com.beautylab.rxt.database

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.beautylab.rxt.models.ArticlesModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ArticlesViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: ArticlesRepository
    val allArticles: LiveData<List<ArticlesModel>>

    init {
        // Gets reference to WordDao from WordRoomDatabase to construct
        // the correct WordRepository.
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