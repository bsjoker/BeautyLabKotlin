package com.beautylab.rxt.di

import android.graphics.drawable.Drawable
import com.beautylab.rxt.models.ArticlesModel
import com.beautylab.rxt.models.RecipeModelForRVGroup

interface CatalogGroupContract {
    interface View {
        fun updateRV(recipesForRV: ArrayList<RecipeModelForRVGroup>)
        fun setData(image: Drawable, text: String)
    }

    interface Presenter {
        fun fillDataFromDB(
            listArticles: List<ArticlesModel>,
            num: Int
        )

    }
}