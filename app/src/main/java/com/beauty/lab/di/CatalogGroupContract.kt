package com.beauty.lab.di

import android.graphics.drawable.Drawable
import com.beauty.lab.models.ArticlesModel
import com.beauty.lab.models.RecipeModelForRVGroup

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